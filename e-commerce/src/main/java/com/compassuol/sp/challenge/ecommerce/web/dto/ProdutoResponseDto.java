package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDto {
    private Long id;
    private String nome;
     private String descricao;
}
