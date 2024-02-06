package com.compassuol.sp.challenge.ecommerce.domain.pedido.repository;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
