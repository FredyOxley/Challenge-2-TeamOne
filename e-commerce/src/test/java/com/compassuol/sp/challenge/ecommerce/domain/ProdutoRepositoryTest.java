package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.QueryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO;
import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;


@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach(){PRODUTO.setId(null);}


    @Test
    public void criarProduto_ComDadosvalidos_RetornaProduto() {
        Produto produto = produtoRepository.save(PRODUTO);

        Produto sut = testEntityManager.find(Produto.class, produto.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getNome()).isEqualTo(produto.getNome());
        assertThat(sut.getDescricao()).isEqualTo(produto.getDescricao());
        assertThat(sut.getValor()).isEqualTo(produto.getValor());
    }

    @Test
    public void criarProduto_ComDadosInvalidos_PropagandoExcecao() {
        Produto emptyProduto = new Produto();
        Produto invalidProduto = new Produto("", "", null);

        assertThatThrownBy(() -> produtoRepository.save(emptyProduto)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> produtoRepository.save(invalidProduto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void criarProduto_ComNomeExistente_PropagandoExcecao() {
        Produto produto = testEntityManager.persistFlushFind(PRODUTO);
        testEntityManager.detach(produto);
        produto.setId(null);
        assertThatThrownBy(() -> produtoRepository.save(produto)).isInstanceOf(RuntimeException.class);
    }


    @Test
    public void buscarProduto_PorIdExistente_RetornarProduto() {
        Produto produto = testEntityManager.persistFlushFind(PRODUTO);
        Optional<Produto> produtoOpt = produtoRepository.findById(produto.getId());

        assertThat(produtoOpt).isNotEmpty();
        assertThat(produtoOpt.get()).isEqualTo(produto);
    }

    @Test
    public void buscarProduto_PorIdInexistente_RetornarVazio() {
        Optional<Produto> produtoOpt = produtoRepository.findById(0L);

        assertThat(produtoOpt).isEmpty();
    }

    @Test
    public void deletarProduto_PorIdExistente_RemoverProdutoDoBancoDeDados() {
        Produto produto = testEntityManager.persistFlushFind(PRODUTO);

        produtoRepository.deleteById(produto.getId());

        Produto deleteProduto = testEntityManager.find(Produto.class, produto.getId());
        assertThat(deleteProduto).isNull();
    }

    @Test
    public void buscarTodos_RetornaNenhumProduto() {
        Example<Produto> query = QueryBuilder.makeQuery(new Produto());

        List<Produto> response = produtoRepository.findAll(query);

        assertThat(response).isEmpty();
    }









}
