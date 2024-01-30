package com.compassuol.sp.challenge.ecommerce.domain.pedido.service;

import com.compassuol.sp.challenge.ecommerce.domain.endereco.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvar(Pedido pedido, Endereco endereco) {
        pedido.setEnderecoEntrega(endereco);
        return pedidoRepository.save(pedido);
    }
}