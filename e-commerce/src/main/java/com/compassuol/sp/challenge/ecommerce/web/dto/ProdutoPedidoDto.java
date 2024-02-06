package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoPedidoDto {
    private String produtoNome;
    private String produtoDescricao;
    private BigDecimal produtoValor;
    private Integer quantidade;
}
