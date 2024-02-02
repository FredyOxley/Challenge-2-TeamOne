package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.EnderecoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.PedidoConstants.*;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EnderecoRepository enderecoRepository;

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



//    @Test
//    public void whenSavePedido_thenItShouldBeRetrieved() {
//        Pedido pedido = new Pedido(); // assuming Pedido has a default constructor
//        pedido = pedidoRepository.save(pedido);
//        Optional<Pedido> retrievedPedido = pedidoRepository.findById(pedido.getId());
//
//        assertThat(retrievedPedido).isNotEmpty();
//        assertThat(retrievedPedido.get()).isEqualTo(pedido);
//    }
//
//
//    @Test
//    public void whenFindAllPedidos_thenAllPedidosShouldBeRetrieved() {
//        Pedido pedido1 = new Pedido(); // assuming Pedido has a default constructor
//        Pedido pedido2 = new Pedido(); // assuming Pedido has a default constructor
//        pedidoRepository.save(pedido1);
//        pedidoRepository.save(pedido2);
//        List<Pedido> pedidos = pedidoRepository.findAll();
//
//        assertThat(pedidos).containsExactlyInAnyOrder(pedido1, pedido2);
//    }



    //testes ENDERECO REPOSITORY
    @Test
    public void whenSaveEndereco_thenItShouldBeRetrieved() {
        Endereco endereco = new Endereco(); // assuming Endereco has a default constructor
        endereco = enderecoRepository.save(endereco);
        Optional<Endereco> retrievedEndereco = enderecoRepository.findById(endereco.getId());

        assertThat(retrievedEndereco).isNotEmpty();
        assertThat(retrievedEndereco.get()).isEqualTo(endereco);
    }

    @Test
    public void whenDeleteEndereco_thenItShouldNotBeRetrieved() {
        Endereco endereco = new Endereco(); // assuming Endereco has a default constructor
        endereco = enderecoRepository.save(endereco);
        enderecoRepository.delete(endereco);
        Optional<Endereco> retrievedEndereco = enderecoRepository.findById(endereco.getId());

        assertThat(retrievedEndereco).isEmpty();
    }

    @Test
    public void whenFindAllEnderecos_thenAllEnderecosShouldBeRetrieved() {
        Endereco endereco1 = new Endereco(); // assuming Endereco has a default constructor
        Endereco endereco2 = new Endereco(); // assuming Endereco has a default constructor
        enderecoRepository.save(endereco1);
        enderecoRepository.save(endereco2);
        List<Endereco> enderecos = enderecoRepository.findAll();

        assertThat(enderecos).containsExactlyInAnyOrder(endereco1, endereco2);
    }




}
