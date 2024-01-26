package com.compassuol.sp.challenge.ecommerce.domain.produto.service;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.HandlerConflictException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoProjection;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.QueryBuilder;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public Produto salvar(Produto produto) {
        try {
            return produtoRepository.save(produto);
        }catch (DataIntegrityViolationException ex)
        {
            throw new HandlerConflictException("Produto já cadastrado no sistema.");
        }
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Produto com id '%s' não encontrado no sistema.", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ProdutoProjection> buscarTodos(Pageable pageable) {
        return produtoRepository.findAllPageable(pageable);
    }

    @Transactional
    public Produto editarProduto(Long id, ProdutoCreateDto produtoCreateDto) {
        Produto produto = buscarPorId(id);
        if (produto != null) {

            produto.setNome(produtoCreateDto.getNome());
            produto.setDescricao(produtoCreateDto.getDescricao());
            produto.setValor(produtoCreateDto.getValor());

            return produtoRepository.save(produto);
        } else {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public void deletarProduto(Long id) {
        buscarPorId(id);
        produtoRepository.deleteById(id);
    }




}

