package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Data;

@Data
public class ViaCepClientDto {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

}
