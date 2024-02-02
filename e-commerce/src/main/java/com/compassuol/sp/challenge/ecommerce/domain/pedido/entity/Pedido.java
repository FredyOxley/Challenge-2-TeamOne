package com.compassuol.sp.challenge.ecommerce.domain.pedido.entity;


import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.MetodoDePagamento;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Produto> produtos;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoDePagamento metodoPagamento;

    @ManyToOne
    private Endereco endereco;

    @Column(name = "valor_sub_total")
    private BigDecimal valorSubTotal;

    @Column(name = "desconto")
    private BigDecimal desconto;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido statusPedido;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;



}
