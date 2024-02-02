package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.PedidoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void buscarProduto_PorIdExistente_RetornarProduto() {
        testEntityManager.persist(PRODUTO);
        testEntityManager.persist(PRODUTO2);
        testEntityManager.persist(ENDERECO);
        Pedido pedido = testEntityManager.persistFlushFind(PEDIDO);
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedido.getId());

        assertThat(pedidoOpt).isNotEmpty();
        assertThat(pedidoOpt.get()).isEqualTo(pedido);
    }

    @Test
    public void buscarPedido_PorIdInexistente_RetornarVazio() {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(0L);

        assertThat(pedidoOpt).isEmpty();
    }
}
