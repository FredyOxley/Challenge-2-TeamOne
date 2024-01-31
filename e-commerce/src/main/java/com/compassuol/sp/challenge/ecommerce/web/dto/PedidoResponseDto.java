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

    //produtos
    private Long id;

    private List<Long> produtosIds;

    //endereco
    private String cep;
    private String numero;
    private String complemento;

    //viacep cidade, rua, estado.


    //pagamentos
    private String metodoPagamento;
    private BigDecimal valorSubTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotal;


    // create date
    private LocalDateTime dataCriacao;
    private String status;
}
