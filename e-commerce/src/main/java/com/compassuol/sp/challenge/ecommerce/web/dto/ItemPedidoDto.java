package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter //separar
@Setter
public  class ItemPedidoDto {
    private Long idProduto;
    private int produtoQuantidade;
}