package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.HandlerConflictException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.controller.ProdutoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO;
import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;


    @Test
    public void CriarProdutot_ComDadosValidos_RetornaOk() throws Exception {
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
}
