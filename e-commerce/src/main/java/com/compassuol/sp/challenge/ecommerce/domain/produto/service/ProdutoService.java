package com.compassuol.sp.challenge.ecommerce.domain.produto.service;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProdutoService{

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
            return produtoRepository.save(produto);
    }



}

