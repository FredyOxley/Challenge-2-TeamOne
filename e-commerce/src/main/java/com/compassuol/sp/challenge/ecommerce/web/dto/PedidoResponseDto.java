package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDto {
    private Long id;
    private String endereco;
    private String metodoPagamento;
    private BigDecimal valorSubTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
    private String status;
    private String motivoCancelamento;
    private LocalDateTime dataCancelamento;
    private List<Long> produtosIds;
}