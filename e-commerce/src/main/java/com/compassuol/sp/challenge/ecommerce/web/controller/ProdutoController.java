package com.compassuol.sp.challenge.ecommerce.web.controller;


import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Produtos", description = "Contém todas as opereções relativas ao recurso de um produto")
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;




}
