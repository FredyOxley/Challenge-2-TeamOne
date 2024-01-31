package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;


import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoResponseDto;
import org.modelmapper.ModelMapper;


public class PedidoMapper {

    ModelMapper modelMapper = new ModelMapper();

public static Pedido toPedido(PedidoCreateDto pedidoCreateDto) {
        return new ModelMapper().map(pedidoCreateDto, Pedido.class);
}


    public static PedidoResponseDto toDto(Pedido pedido) {
       return new ModelMapper().map(pedido, PedidoResponseDto.class);
    }


}