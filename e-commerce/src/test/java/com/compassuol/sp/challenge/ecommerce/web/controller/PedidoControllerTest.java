package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCancelDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.PedidoConstants.PEDIDO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.is;


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



    @Test
    public void atualizarStatusWithValidIdReturnsUpdatedPedido() throws Exception {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatusPedido(StatusPedido.CONFIRMADO);

        when(pedidoService.atualizarStatus(any())).thenReturn(pedido);

        mockMvc.perform(put("/api/orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(pedido.getId().intValue())))
//                .andExpect(jsonPath("$.statusPedido", is(pedido.getStatusPedido().toString())));

    }

    @Test
    public void atualizarStatusWithInvalidIdReturnsNotFound() throws Exception {
        Long id = 1L;

        when(pedidoService.atualizarStatus(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/api/orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



        @Test
        public void cancelarPedidoWithValidIdReturnsCanceledPedido() throws Exception {
            Long id = 1L;
            Pedido pedido = new Pedido();
            pedido.setId(id);
            PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();

            when(pedidoService.cancelarPedido(any(), any())).thenReturn(pedido);

            mockMvc.perform(post("/api/orders/" + id + "/cancel")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(pedidoCancelDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(pedido.getId().intValue())));
        }

    @Test
    public void cancelarPedidoWithInvalidIdReturnsNotFound() throws Exception {
        Long id = 1L;
        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();

        when(pedidoService.cancelarPedido(any(), any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post("/api/orders/" + id + "/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pedidoCancelDto)))
                .andExpect(status().isNotFound());
    }



        private String asJsonString(final Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



    }




