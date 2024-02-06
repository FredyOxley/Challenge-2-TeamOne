package com.compassuol.sp.challenge.ecommerce.domain.pedido.repository;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<PedidoProjection> findByStatusPedido(StatusPedido statusPedido, Pageable pageable);

    @Query("select p from Pedido p")
    Page<PedidoProjection> findAllPageable(Pageable pageable);
}
