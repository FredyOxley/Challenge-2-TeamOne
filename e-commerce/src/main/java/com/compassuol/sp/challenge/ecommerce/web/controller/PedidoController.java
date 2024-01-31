package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Pedidos", description = "Contém todas as operações relativas ao recurso de um pedido")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class PedidoController {

    private final PedidoService pedidoService;


//    @Operation(summary = "Criar um novo pedido", description = "Recurso para criar um novo pedido.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Dados inválidos no pedido enviado"),
//            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
@PostMapping
public ResponseEntity<PedidoCreateDto> criarPedido(@RequestBody @Valid PedidoCreateDto pedidoCreateDto) {
    try {
        Pedido pedido = PedidoMapper.toPedido(pedidoCreateDto);
        Pedido pedidoCriado = pedidoService.criarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body((pedidoCreateDto));
    } catch (DataIntegrityViolationException ex) {
        throw new DataIntegrityViolationException("Erro ao criar pedido");
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoCreateDto pedidoCreateDto) {
        Pedido novoPedido = PedidoMapper.toPedido(pedidoCreateDto);
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, novoPedido);
        return ResponseEntity.ok(PedidoMapper.toDto(pedidoAtualizado));
    }


}