package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProdutoConstants.PRODUTO;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach(){PRODUTO.setId(null);}

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
    public void deleteProduto_WithExistingId_RemovesProdutoFromDatabase() {
        Produto produto = testEntityManager.persistFlushFind(PRODUTO);

        produtoRepository.deleteById(produto.getId());

        Produto deleteProduto = testEntityManager.find(Produto.class, produto.getId());
        assertThat(deleteProduto).isNull();
    }

}