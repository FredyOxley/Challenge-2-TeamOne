package com.compassuol.sp.challenge.ecommerce.domain.produto.repository;

import java.math.BigDecimal;

public interface
ProdutoProjection {

    Long getId();

    String getNome();

    String getDescricao();

    BigDecimal getValor();


}
