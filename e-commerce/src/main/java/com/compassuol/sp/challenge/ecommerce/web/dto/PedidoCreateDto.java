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

    @Getter //separar
    @Setter
    public static class ItemPedidoDto {
        private Long idProduto;
        private int quantidade;
    }

    @Getter //separar
    @Setter
    public static class EnderecoDto {
        private Integer numero;
        private String complemento;
        private String cep;
    }
}
