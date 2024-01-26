package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.controller.ProdutoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO2;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

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
}
