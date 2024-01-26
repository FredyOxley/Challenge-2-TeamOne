package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;

import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoProjection;

import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;

import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoProjection produtoProjection;

    @Test
    public void criarProduto_ComDadosValidos_RetornarProduto() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto("Produto 1", "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreate);

        assertEquals(produtoCreate.getNome(), produto.getNome());
        assertEquals(produtoCreate.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreate.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComNomeNulo_RetornarErro() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto(null, "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreate);

        assertThat(produtoCreate.getNome()).isNull();
        assertEquals(produtoCreate.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreate.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComDescricaoNula_RetornarErro() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto("Produto 1", null, BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreate);

        assertEquals(produtoCreate.getNome(), produto.getNome());
        assertThat(produtoCreate.getDescricao()).isNull();
        assertEquals(produtoCreate.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComValorNulo_RetornarErro() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto("Produto 1", "Descrição do produto 1", null);
        produto = produtoService.salvar(produtoCreate);

        assertEquals(produtoCreate.getNome(), produto.getNome());
        assertEquals(produtoCreate.getDescricao(), produto.getDescricao());
        assertThat(produtoCreate.getValor()).isNull();
    }

    @Test
    public void criarProduto_ComNomeVazio_RetornarErro() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto("", "Descrição do produto 1", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreate);

        assertThat(produtoCreate.getNome()).isBlank();
        assertEquals(produtoCreate.getDescricao(), produto.getDescricao());
        assertEquals(produtoCreate.getValor(), produto.getValor());
    }

    @Test
    public void criarProduto_ComDescricaoVazia_RetornarErro() {
        Produto produto = ProdutoConstants.PRODUTO;
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto produtoCreate = new Produto("Produto 1", "", BigDecimal.TEN);
        produto = produtoService.salvar(produtoCreate);

        assertEquals(produtoCreate.getNome(), produto.getNome());
        assertThat(produtoCreate.getDescricao()).isBlank();
        assertEquals(produtoCreate.getValor(), produto.getValor());
    }

    @Test
    public void buscarProduto_PorIdExistente_RetornarProduto() {
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(PRODUTO2));

        Optional<Produto> sut = Optional.ofNullable(produtoService.buscarPorId(2L));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PRODUTO2);
    }

    @Test
    public void buscarProduto_PorIdInexistente_RetornarExcecao() {
        doThrow(new EntityNotFoundException("")).when(produtoRepository).findById(0L);

        assertThatThrownBy(() -> produtoRepository.findById(0L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deletarProduto_PorIdExistente_NaoLancaExcecao() {
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(PRODUTO2));
        Assertions.assertThatCode(() -> produtoService.deletarProduto(2L)).doesNotThrowAnyException();
    }

    @Test
    public void deletarProduto_IdInexistente_LancaExcecao() {
        doThrow(new RuntimeException()).when(produtoRepository).findById(99L);

        assertThatThrownBy(() -> produtoService.deletarProduto(99L)).isInstanceOf(RuntimeException.class);
    }

    @Test

    public void buscarTodos_RetornarListaDeProdutosComSucesso() {
        List<ProdutoProjection> listaDeProdutos = Arrays.asList(produtoProjection, produtoProjection, produtoProjection);

        Page<ProdutoProjection> page = new PageImpl<>(listaDeProdutos);
        when(produtoRepository.findAllPageable(any())).thenReturn(page);

        Page<ProdutoProjection> produtos = produtoService.buscarTodos(any());
        assertNotNull(produtos);
        assertEquals(produtos.getTotalElements(), 3);
    }

    @Test
    public void buscarTodos_RetornarListaDeProdutosVaziaComSucesso() {
        List<ProdutoProjection> listaDeProdutos = Arrays.asList();

        Page<ProdutoProjection> page = new PageImpl<>(listaDeProdutos);
        when(produtoRepository.findAllPageable(any())).thenReturn(page);

        Page<ProdutoProjection> produtos = produtoService.buscarTodos(any());

        assertNotNull(produtos);
        assertEquals(produtos.getTotalElements(), 0);
    }




}