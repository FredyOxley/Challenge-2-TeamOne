package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Pedido toPedido(PedidoCreateDto createDto) {
        return mapper.map(createDto, Pedido.class);
    }

    public static PedidoResponseDto toDto(Pedido pedido) {
        return mapper.map(pedido, PedidoResponseDto.class);
    }

}