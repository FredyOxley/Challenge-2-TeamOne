package com.compassuol.sp.challenge.ecommerce.domain.produto.service;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService{

    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;




    public Produto salvar(ProdutoCreateDto produtoCreateDTO) {
        Produto produto = modelMapper.map(produtoCreateDTO, Produto.class);
        return produtoRepository.save(produto);
    }


}

