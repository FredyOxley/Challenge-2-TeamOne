package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProdutoRepository produtoRepository;
    @Test
    public void criarProduto_ComDadosValidos_RetornarProduto(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Produto 1", "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreateDto);

        assertEquals(produtoCreateDto.getNome(), produto.getNome());
        assertEquals(produtoCreateDto.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreateDto.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComNomeNulo_RetornarErro(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto(null, "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreateDto);

        assertThat(produtoCreateDto.getNome()).isNull();
        assertEquals(produtoCreateDto.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreateDto.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComDescricaoNula_RetornarErro(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Produto 1", null, BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreateDto);

        assertEquals(produtoCreateDto.getNome(), produto.getNome());
        assertThat(produtoCreateDto.getDescricao()).isNull();
        assertEquals(produtoCreateDto.getValor(), produto.getValor());
    }
    @Test
    public void criarProduto_ComValorNulo_RetornarErro(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Produto 1", "Descrição do produto 1", null);
        produto = produtoService.salvar(produtoCreateDto);

        assertEquals(produtoCreateDto.getNome(), produto.getNome());
        assertEquals(produtoCreateDto.getDescricao(), produto.getDescricao());
        assertThat(produtoCreateDto.getValor()).isNull();
    }

    @Test
    public void criarProduto_ComNomeVazio_RetornarErro(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("", "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreateDto);

        assertThat(produtoCreateDto.getNome()).isBlank();
        assertEquals(produtoCreateDto.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreateDto.getValor(), produto.getValor());
    }
    @Test
    public void criarProduto_ComDescricaoVazia_RetornarErro(){
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        ProdutoCreateDto produtoCreateDto = new ProdutoCreateDto("Produto 1", "", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreateDto);

        assertEquals(produtoCreateDto.getNome(), produto.getNome());
        assertThat(produtoCreateDto.getDescricao()).isBlank();
        assertEquals(produtoCreateDto.getValor(), produto.getValor());
    }
}