package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


        @Operation(summary = "Criar um novo pedido", description = "Recurso para criar um novo pedido.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos no pedido enviado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        })
        @PostMapping
        public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoCreateDto pedidoDto) {
            try {
                Pedido pedido = new Pedido();
                Pedido pedidoCriado = pedidoService.salvar(pedido);
                return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

