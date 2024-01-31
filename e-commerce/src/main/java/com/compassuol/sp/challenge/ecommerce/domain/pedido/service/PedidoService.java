package com.compassuol.sp.challenge.ecommerce.domain.pedido.service;


import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.MetodoDePagamento;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.EnderecoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.client.ViaCepClient;
import com.compassuol.sp.challenge.ecommerce.web.dto.PedidoCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ViaCepClientDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final ProdutoService produtoService;
    private final ViaCepClient viaCepClient;
    private final ModelMapper modelMapper;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoRepository enderecoRepository;

    public Pedido salvar(PedidoCreateDto pedidoCreateDto)
    {
        Endereco endereco = buscarEnderecoPorCep(pedidoCreateDto.getEndereco());

        Pedido pedidoParaCriar = new Pedido();
        pedidoParaCriar.setMetodoPagamento(MetodoDePagamento.valueOf(pedidoCreateDto.getMetodoPagamento()));
        pedidoParaCriar.setEndereco(endereco);
        List<Produto> produtos = new ArrayList<>();

        for (PedidoCreateDto.ItemPedidoDto item : pedidoCreateDto.getProdutos()) {
            Produto produtoId = produtoService.buscarPorId(item.getIdProduto());
            produtos.add(produtoId);
        }

        pedidoParaCriar.setProdutos(produtos);
        pedidoParaCriar.setStatusPedido(StatusPedido.CONFIRMADO);

        return pedidoRepository.save(pedidoParaCriar);
    }

    private Endereco buscarEnderecoPorCep(PedidoCreateDto.EnderecoDto enderecoDto)
    {
        ViaCepClientDto viaCepClientDto = viaCepClient.findByCep(enderecoDto.getCep());
        Endereco endereco = modelMapper.map(viaCepClientDto, Endereco.class);
        endereco.setNumeroEndereco(enderecoDto.getNumero());
        endereco.setCidade(viaCepClientDto.getLocalidade());

        return enderecoRepository.save(endereco);
    }

}
