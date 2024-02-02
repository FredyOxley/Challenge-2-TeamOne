package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
    private Integer numero;
    private String complemento;
    private String cep;
}