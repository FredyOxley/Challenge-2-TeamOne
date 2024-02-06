package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.web.dto.PageablePedidoDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageablePedidoMapper {

    public static PageablePedidoDto toDto(Page page) {
        return new ModelMapper().map(page, PageablePedidoDto.class);
    }
}
