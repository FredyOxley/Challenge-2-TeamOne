package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoCreateDto {

    private List<ItemPedidoDto> produtos;
    private EnderecoDto endereco;
    private String metodoPagamento;
}
