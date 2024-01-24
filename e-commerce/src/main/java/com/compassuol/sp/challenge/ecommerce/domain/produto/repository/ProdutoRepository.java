package com.compassuol.sp.challenge.ecommerce.domain.produto.repository;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
