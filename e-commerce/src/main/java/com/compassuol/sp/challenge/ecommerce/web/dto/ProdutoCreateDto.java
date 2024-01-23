package com.compassuol.sp.challenge.ecommerce.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Not;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCreateDto {


    @NotBlank
    @NotNull
    private String nome;

    @NotBlank
    @Size(min = 10, max = 200)
    private String descricao;

    @NotNull
    @NotBlank
    private BigDecimal valor;


}
