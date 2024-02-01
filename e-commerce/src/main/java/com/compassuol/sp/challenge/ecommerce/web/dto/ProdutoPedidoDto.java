package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoPedidoDto {



    private String nome;


    private String descricao;


    private BigDecimal valor;

    private int produtoQuantidade;

}
