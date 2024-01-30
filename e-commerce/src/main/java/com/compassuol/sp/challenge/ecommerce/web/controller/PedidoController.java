package com.compassuol.sp.challenge.ecommerce.web.controller;


import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pedidos", description = "Contém todas as operações relativas ao recurso de um pedido")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class PedidoController {

    private final PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody @Valid PedidoCreateDto pedidoCreateDto) {
        Pedido pedido = PedidoMapper.toPedido(pedidoCreateDto);
        Pedido pedidoSalvo = pedidoService.salvar(pedido);
        PedidoResponseDto pedidoResponseDto = PedidoMapper.toDto(pedidoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDto);
    }
}
