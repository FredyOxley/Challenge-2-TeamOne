package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto {
    private Integer numero;
    private String complemento;
    private String cep;
}