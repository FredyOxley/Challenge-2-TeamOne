package com.compassuol.sp.challenge.ecommerce.domain.produto.repository;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    public static Example<Produto> makeQuery(Produto produto) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(produto, exampleMatcher);
    }
}