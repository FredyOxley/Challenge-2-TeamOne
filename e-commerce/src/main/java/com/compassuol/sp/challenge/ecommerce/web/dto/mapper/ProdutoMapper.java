package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProdutoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProdutoMapper {

    public static Produto toProduto(ProdutoCreateDto createDto) {

        return new ModelMapper().map(createDto, Produto.class);
    }

    public static ProdutoResponseDto toDto(Produto produto) {
        return new ModelMapper().map(produto, ProdutoResponseDto.class);
    }

}
