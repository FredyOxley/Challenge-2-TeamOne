package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.HandlerConflictException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoProjection;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO;
import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO2;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Test
    public void CriarProduto_ComDadosValidos_RetornaOk() throws Exception {
        when(produtoService.salvar(PRODUTO2)).thenReturn(PRODUTO2);

        mockMvc
                .perform(
                        post("/api/products").content(objectMapper.writeValueAsString(PRODUTO2))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void CriarProduto_ComDadosInvalidos_RetornaBadRequest() throws Exception {
        Produto emptyProduto = new Produto();
        Produto invalidProduto = new Produto("", "", null);

        mockMvc
                .perform(
                        post("/api/products").content(objectMapper.writeValueAsString(emptyProduto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
        mockMvc
                .perform(
                        post("/api/products").content(objectMapper.writeValueAsString(invalidProduto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void CriarProduto_ComNomeExistente_RetornaConflito() throws Exception {
        when(produtoService.salvar(any())).thenThrow(HandlerConflictException.class);

        mockMvc
                .perform(
                        post("/api/products").content(objectMapper.writeValueAsString(PRODUTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void buscarProduto_PorIdExistente_RetornarProduto() throws Exception {
        when(produtoService.buscarPorId(2L)).thenReturn(PRODUTO2);

        mockMvc.perform(get("/api/products/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarProduto_PorIdInexistente_RetornarErroComStatus404() throws Exception {
        when(produtoService.buscarPorId(0L)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/api/products/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscarProduto_PorParametroInvalido_RetornarErroComStatus400() throws Exception {
        mockMvc.perform(get("/api/products/invalidID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletarProduto_PorIdExistente_RetornaNoContent() throws Exception {
        mockMvc.perform(delete("/api/products/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletarProduto_PorIdInexistente_RetornaNotFound() throws Exception {
        final Long produtoId = 1L;

        doThrow(new EmptyResultDataAccessException(1)).when(produtoService).deletarProduto(produtoId);

        mockMvc.perform(delete("/api/products/" + produtoId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAll_WithDefaultPageable_ReturnsPageableDto() throws Exception {
        Page<ProdutoProjection> produtos = new PageImpl<>(Collections.singletonList(new ProdutoProjection() {
            public Long getId() {
                return 1L;
            }

            public String getNome() {
                return "Test Product";
            }

            public String getDescricao() {
                return "Test Description";
            }

            public BigDecimal getValor() {
                return BigDecimal.valueOf(100.0);
            }
        }));

        when(produtoService.buscarTodos(any())).thenReturn(produtos);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.content[0].nome", Matchers.is("Test Product")))
                .andExpect(jsonPath("$.content[0].descricao", Matchers.is("Test Description")))
                .andExpect(jsonPath("$.content[0].valor", Matchers.is(100.0)));
    }

    @Test
    public void getAll_WithNoProducts_ReturnsEmptyPageableDto() throws Exception {
        Page<ProdutoProjection> produtos = new PageImpl<>(Collections.emptyList());

        when(produtoService.buscarTodos(any())).thenReturn(produtos);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }


    @Test
    public void atualizarProduto_WithValidIdAndData_ReturnsUpdatedProduct() throws Exception {
        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Updated Product", "Updated Description", BigDecimal.valueOf(200.0));
        Produto updatedProduct = new Produto(2L, "Produto Atualizado", "Descrição Atualizada", BigDecimal.valueOf(200.0));

        when(produtoService.editarProduto(anyLong(), any())).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoCreateDto)))
                .andExpect(status().isOk());
//               .andExpect(jsonPath("$.id", Matchers.is(2)))
//               .andExpect(jsonPath("$.nome", Matchers.is("Produto Atualizado")))
//               .andExpect(jsonPath("$.descricao", Matchers.is("Descrição Atualizada")))
//              .andExpect(jsonPath("$.valor", Matchers.is(200.0)));
    }

    @Test
    public void atualizarProduto_WithInvalidId_ReturnsNotFound() throws Exception {
        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Updated Product", "Updated Description", BigDecimal.valueOf(200.0));

        when(produtoService.editarProduto(anyLong(), any())).thenThrow(new EntityNotFoundException("Product not found"));

        mockMvc.perform(put("/api/products/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoCreateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void atualizarProduto_WithInvalidData_ReturnsUnprocessableEntity() throws Exception {
        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("", "", BigDecimal.valueOf(-1));

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoCreateDto)))
                .andExpect(status().isUnprocessableEntity());
    }
}
