package com.compassuol.sp.challenge.ecommerce.domain.pedido.service;

import com.compassuol.sp.challenge.ecommerce.domain.endereco.entity.EnderecoCep;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EnderecoCep enderecoCep;


    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }



}