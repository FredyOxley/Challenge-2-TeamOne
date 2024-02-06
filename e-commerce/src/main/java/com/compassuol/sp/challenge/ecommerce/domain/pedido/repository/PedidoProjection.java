package com.compassuol.sp.challenge.ecommerce.domain.pedido.repository;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.ItemPedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoProjection {

    Long getId();

    List<ItemPedido> getProdutos();

    Endereco getEndereco();

    String getMetodoPagamento();

    Double getValorSubTotal();

    Double getDesconto();

    Double getValorTotal();

    LocalDateTime getDataCriacao();

    StatusPedido getStatusPedido();
}
