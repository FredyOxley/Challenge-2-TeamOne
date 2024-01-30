package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDto {
    private String endereco;
    private String metodoPagamento;
    private List<Long> produtosIds;
}