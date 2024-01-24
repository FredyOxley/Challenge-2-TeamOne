package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.exception.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Produtos", description = "Contém todas as operações relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Operation(summary = "Criar um novo produto",
            description = "Recurso para criar um novo produto no sistema. ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProdutoResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Campo invalido! Descrição deve conter 10 caracteres ou mais e o nome do produto não pode ser nulo ou vazio",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoCreateDto produtoCreateDTO) {
        Produto produtoCriado = produtoService.salvar(produtoCreateDTO);
        return ResponseEntity.ok(produtoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> getById(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toDto(produto));
    }

    @Operation(summary = "Deletar um produto por ID" ,
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
    @ResponseStatus( code = HttpStatus.NO_CONTENT)
    public void deletarProdutoPorId(@PathVariable Long id) {
        var produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        produtoRepository.delete(produtoOptional.get());
    }
}
