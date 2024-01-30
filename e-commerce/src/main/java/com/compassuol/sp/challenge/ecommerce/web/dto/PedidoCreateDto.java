package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class PedidoCreateDto {
    private List<Produto> idProduto;
    private Long enderecoEntregaId;
    private String metodoPagamento;
    private BigDecimal valorSubTotal;
    private BigDecimal valorTotal;
}
