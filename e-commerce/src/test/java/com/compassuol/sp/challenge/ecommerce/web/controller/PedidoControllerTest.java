package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.PedidoConstants.PEDIDO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PedidoService pedidoService;

    @Test
    public void buscarPedido_PorIdExistente_RetornarPedido() throws Exception {
        when(pedidoService.buscarPorId(2L)).thenReturn(PEDIDO);

        mockMvc.perform(get("/api/orders/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPedido_PorIdInexistente_RetornarErroComStatus404() throws Exception {
        when(pedidoService.buscarPorId(0L)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/api/orders/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscarPedido_PorParametroInvalido_RetornarErroComStatus400() throws Exception {
        mockMvc.perform(get("/api/orders/invalidID"))
                .andExpect(status().isBadRequest());
    }

}
