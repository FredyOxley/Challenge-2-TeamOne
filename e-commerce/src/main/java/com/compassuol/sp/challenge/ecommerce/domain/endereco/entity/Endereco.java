package com.compassuol.sp.challenge.ecommerce.domain.endereco.entity;

import jakarta.persistence.*;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String Endereco; // Endereço de entrega


}