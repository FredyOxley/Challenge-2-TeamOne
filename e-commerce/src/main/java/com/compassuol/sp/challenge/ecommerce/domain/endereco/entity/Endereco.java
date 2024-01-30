package com.compassuol.sp.challenge.ecommerce.domain.endereco.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String Endereco; // Endereço de entrega

    @Column(name = "cep", nullable = false)
    private String cep; // CEP de entrega

    @Column(name = "logradouro", nullable = false)
    private String logradouro; // Bairro de entrega


}