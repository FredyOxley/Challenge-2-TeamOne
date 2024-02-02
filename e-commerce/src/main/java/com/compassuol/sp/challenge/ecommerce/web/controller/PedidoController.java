package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PageableDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCancelDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PageableMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedidos", description = "Contém todas as operações relativas ao recurso de um pedido")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class PedidoController {

    private final ModelMapper mapper;
    private final PedidoService pedidoService;


    @Operation(summary = "Criar um novo pedido", description = "Recurso para criar um novo pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no pedido enviado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDto> criar(@Valid @RequestBody PedidoCreateDto pedidoDto) {

        Pedido pedidoCriado = pedidoService.salvar(pedidoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(pedidoCriado, PedidoResponseDto.class));
    }

    @Operation(summary = "Localizar um pedido", description = "Recurso para localizar um pedido pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido localizado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PedidoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado no sistema",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(mapper.map(pedido, PedidoResponseDto.class));
    }

    @PutMapping("/{idPedido}")
   public ResponseEntity<PedidoResponseDto> Atualizar(@PathVariable Long idPedido) {
       Pedido pedidoEnviado = pedidoService.atualizarStatus(idPedido);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(pedidoEnviado, PedidoResponseDto.class));
   }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id, @RequestBody PedidoCancelDto pedidoCancelDto) {

        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, pedidoCancelDto);

        return ResponseEntity.ok(pedidoCancelado);
    }

    @Operation(summary = "Recuperar lista de pedidos",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            description = "Número da página a ser recuperada (padrão: 0)",
                            schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            description = "Número de elementos por página (padrão: 5)",
                            schema = @Schema(type = "integer", defaultValue = "5")),
                    @Parameter(in = ParameterIn.QUERY, name = "sort",
                            description = "Ordenação dos resultados. Formato: campo,desc ou campo, asc",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "dataCriacao,desc"))
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "status",
                            description = "Filtro para recuperar pedidos por status (opcional)",
                            schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos recuperados com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pedidos não encontrados",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))
                    )
            })
    @GetMapping
    public ResponseEntity<PageableDto> listarTodosPedidos(
            @PageableDefault(size = 5, sort = {"dataCriacao"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "status", required = false) String status) {

        Page<PedidoResponseDto> pedidos = pedidoService.buscarTodosPedidos(pageable, status);

        return ResponseEntity.ok(PageableMapper.toDto(pedidos));
    }

}
