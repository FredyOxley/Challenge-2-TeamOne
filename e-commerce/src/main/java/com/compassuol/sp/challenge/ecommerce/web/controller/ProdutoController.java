package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.ProdutoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoCreateDto produtoCreateDTO) {
        Produto produtoCriado = produtoService.salvar(produtoCreateDTO);
        return ResponseEntity.ok(produtoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> getById(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toDto(produto));
    }


}
