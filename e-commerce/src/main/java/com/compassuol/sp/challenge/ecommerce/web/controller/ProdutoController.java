package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoProjection;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.PageableDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PageableMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Produtos", description = "Contém todas as operações relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProdutoController {

    private final ModelMapper modelMapper;
    private final ProdutoService produtoService;

    @Operation(summary = "Criar um novo produto",
            description = "Recurso para criar um novo produto no sistema. ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Campo invalido! Descrição deve conter 10 caracteres ou mais e o nome do produto não pode ser nulo ou vazio",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Produto já cadastrado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<ProdutoResponseDto> criarProduto(@RequestBody @Valid ProdutoCreateDto produtoCreateDTO) {
        Produto produtoCriado = modelMapper.map(produtoCreateDTO, Produto.class);
        produtoService.salvar(produtoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(produtoCriado, ProdutoResponseDto.class));
    }

    @Operation(summary = "Localizar um produto", description = "Recurso para localizar um produto pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto localizado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado no sistema",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(modelMapper.map(produto, ProdutoResponseDto.class));
    }

    @Operation(summary = "Deletar um produto por ID",
            description = "Recurso para deletar um produto do sistema por ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado no sistema.",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos.",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar lista de produtos",
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado!",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))
                    )
            })
    @GetMapping
    public ResponseEntity<PageableDto> buscarTodosOsProdutos(@Parameter(hidden = true)
                                                             @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        Page<ProdutoProjection> produtos = produtoService.buscarTodos(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(produtos));
    }

    @Operation(summary = "Atualizar um produto existente",
            description = "Recurso para atualizar as informações de um produto existente no sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "422", description = "Campo inválido! Descrição deve conter 10 caracteres ou mais, o nome do produto não pode ser nulo ou vazio e o valor não pode ser negativo",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoCreateDto produtoCreateDTO) {
        Produto produto = produtoService.editarProduto(id, produtoCreateDTO);
        return ResponseEntity.ok(modelMapper.map(produto, ProdutoResponseDto.class));
    }

}
