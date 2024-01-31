package com.compassuol.sp.challenge.ecommerce.domain.endereco.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id; // ID do endereço

    @Column(name = "numero")
    private String NumeroDeEndereco; // Endereço de entrega

    @Column(name = "cep")
    private String cep; // CEP de entrega


    @Column(name = "complemento")
    private String complemento; // Complemento de entrega


    @Column(name = "rua")
    private String rua; // Rua de entrega

    @Column(name = "cidade")
    private String cidade; // Cidade de entrega

    @Column(name = "estado")
    private String estado; // Estado de entrega

    public void setNumero(String enderecoNumero) {
        this.NumeroDeEndereco = enderecoNumero;
    }
}
