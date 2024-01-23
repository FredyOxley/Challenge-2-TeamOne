package com.compassuol.sp.challenge.ecommerce.domain.produto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Column(name = "valor", nullable = false)
    @Positive(message = "O valor deve ser um número positivo")
    private BigDecimal valor;


    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;

        return Objects.equals(id, produto.id);
    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
