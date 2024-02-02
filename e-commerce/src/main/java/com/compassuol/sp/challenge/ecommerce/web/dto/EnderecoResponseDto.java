package com.compassuol.sp.challenge.ecommerce.web.dto;


import lombok.Data;

@Data
public class EnderecoResponseDto {


    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;
    private String cep;



}
