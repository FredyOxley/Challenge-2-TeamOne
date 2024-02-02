package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCancelDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldUpdateOrderStatusSuccessfully() {
        Long idPedido = 1L;
        Pedido pedido = new Pedido();
        PedidoResponseDto pedidoResponseDto = new PedidoResponseDto();

        when(pedidoService.atualizarStatus(idPedido)).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoResponseDto.class)).thenReturn(pedidoResponseDto);

        ResponseEntity<PedidoResponseDto> response = pedidoController.Atualizar(idPedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoResponseDto, response.getBody());
    }

//    @Test
//     public void shouldReturnNotFoundWhenUpdatingNonExistingOrder() {
//
//        Long idPedido = 1L;
//
//        when(pedidoService.atualizarStatus(idPedido)).thenReturn(null);
//
//        ResponseEntity<PedidoResponseDto> response = pedidoController.Atualizar(idPedido);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }


    @Test
    public void shouldCancelOrderSuccessfully() {
        Long id = 1L;
        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();
        Pedido pedido = new Pedido();

        when(pedidoService.cancelarPedido(id, pedidoCancelDto)).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.cancelarPedido(id, pedidoCancelDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

//    @Test
//    public void shouldReturnNotFoundWhenCancellingNonExistingOrder() {
//        Long id = 1L;
//        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();
//
//        when(pedidoService.cancelarPedido(id, pedidoCancelDto)).thenReturn(null);
//
//        ResponseEntity<Pedido> response = pedidoController.cancelarPedido(id, pedidoCancelDto);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}