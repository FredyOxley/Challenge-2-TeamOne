package com.compassuol.sp.challenge.ecommerce.domain.pedido.entity;


import com.compassuol.sp.challenge.ecommerce.domain.endereco.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToMany
    @JoinTable( name = "id_products")

    private List<Produto> produto;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco enderecoEntrega;

    @Column(name = "metodo_pagamento", nullable = false)
    private String metodoPagamento;

    @Column(name = "valor_sub_total", nullable = false)
    private BigDecimal valorSubTotal;

    @Column(name = "desconto", nullable = false)
    private BigDecimal desconto;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    public enum StatusPedido {
        CONFIRMADO, ENVIADO, CANCELADO
    }

    public enum MetodoDePagamento {
        CARTAO_CREDITO, TRANSFERENCIA_BANCARIA, CRYPTOMOEDA, GIFT_CARD, PIX, OUTROS
    }



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pedido pedido = (Pedido) o;
        return getId() != null && Objects.equals(getId(), pedido.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
