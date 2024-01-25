package com.compassuol.sp.challenge.ecommerce.domain.produto.repository;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select c from Produto c")
    Page<ProdutoProjection> findAllPageable(Pageable pageable);

}
