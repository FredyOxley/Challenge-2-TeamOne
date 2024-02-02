package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDto {

    @NotNull
    private Long id;
    private List<ProdutoPedidoDto> produtos;
    private EnderecoResponseDto endereco;
    private String metodoPagamento;
    private Double valorSubTotal;
    private Double desconto;
    private Double valorTotal;
    private LocalDateTime dataCriacao;
    private String status;
}
