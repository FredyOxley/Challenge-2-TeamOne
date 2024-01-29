package com.compassuol.sp.challenge.ecommerce.domain.pedido.repository;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
