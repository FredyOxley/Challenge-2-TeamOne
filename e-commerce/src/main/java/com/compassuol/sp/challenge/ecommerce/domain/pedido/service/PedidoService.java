package com.compassuol.sp.challenge.ecommerce.domain.pedido.service;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.MetodoDePagamento;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.EnderecoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.BadRequestException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.client.ViaCepClient;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final ProdutoService produtoService;
    private final ViaCepClient viaCepClient;
    private final ModelMapper modelMapper;
    private final PedidoRepository pedidoRepository;
    private final EnderecoRepository enderecoRepository;

    public Pedido salvar(PedidoCreateDto pedidoCreateDto) {
        Endereco endereco = buscarEnderecoPorCep(pedidoCreateDto.getEndereco());

        Pedido pedidoParaCriar = new Pedido();
        pedidoParaCriar.setMetodoPagamento(MetodoDePagamento.valueOf(pedidoCreateDto.getMetodoPagamento()));
        pedidoParaCriar.setEndereco(endereco);
        List<Produto> produtos = new ArrayList<>();
        BigDecimal totalValorProduto = BigDecimal.ZERO;

        for (ItemPedidoDto item : pedidoCreateDto.getProdutos()) {
            Produto produtoId = produtoService.buscarPorId(item.getIdProduto());
            produtos.add(produtoId);
            pedidoParaCriar.setValorSubTotal(produtoId.getValor().multiply(BigDecimal.valueOf(item.getProdutoQuantidade())));
            totalValorProduto = totalValorProduto.add(pedidoParaCriar.getValorSubTotal());
        }

        pedidoParaCriar.setProdutos(produtos);
        pedidoParaCriar.setValorSubTotal(totalValorProduto);
        pedidoParaCriar.setStatusPedido(StatusPedido.CONFIRMADO);
        pedidoParaCriar.setDataCriacao(LocalDateTime.parse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME)));


        if (pedidoParaCriar.getMetodoPagamento() != MetodoDePagamento.PIX) {
            pedidoParaCriar.setDesconto(BigDecimal.valueOf(0));
            pedidoParaCriar.setValorTotal(pedidoParaCriar.getValorSubTotal());
            return pedidoRepository.save(pedidoParaCriar);
        } else {
            pedidoParaCriar.setDesconto(pedidoParaCriar.getValorSubTotal().multiply(BigDecimal.valueOf(0.05)));
            pedidoParaCriar.setValorTotal(pedidoParaCriar.getValorSubTotal().subtract(pedidoParaCriar.getDesconto()));
            return pedidoRepository.save(pedidoParaCriar);
        }
    }


    public Endereco buscarEnderecoPorCep(EnderecoDto enderecoDto) {
        ViaCepClientDto viaCepClientDto = viaCepClient.findByCep(enderecoDto.getCep());
        Endereco endereco = modelMapper.map(viaCepClientDto, Endereco.class);
        endereco.setNumeroEndereco(enderecoDto.getNumero());
        endereco.setCidade(viaCepClientDto.getLocalidade());

        return enderecoRepository.save(endereco);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Pedido com id '%s' não encontrado no sistema.", id))
        );
    }

    @Transactional
    public Pedido atualizarStatus(Long id) {
        Pedido pedido = buscarPorId(id);

        if (pedido.getStatusPedido() == StatusPedido.CONFIRMADO) {
            pedido.setStatusPedido(StatusPedido.ENVIADO);
            return pedidoRepository.save(pedido);
        } else {
            throw new IllegalStateException("O status do pedido não é 'Confirmado', portanto, não pode ser alterado para 'Enviado'");
        }
    }

    public Pedido cancelarPedido(Long pedidoId, PedidoCancelDto pedidoCancelDto) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontra com ID: " + pedidoId));

        validarPedidoParaCancelar(pedido);
        pedido.setMotivoCancelamento(pedidoCancelDto.getMotivoCancelamento());
        pedido.setDataCancelamento(LocalDateTime.parse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME)));
        pedido.setStatusPedido(StatusPedido.CANCELADO);

        return pedidoRepository.save(pedido);
    }

    public void validarPedidoParaCancelar(Pedido pedido) {

        if (StatusPedido.ENVIADO.equals(pedido.getStatusPedido())) {
            throw new IllegalStateException("Não é possível cancelar um pedido"); //COLOCAR NO HANDLER
        }

        LocalDateTime limiteCancelar = pedido.getDataCriacao().plusDays(90);
        if (LocalDateTime.now().isAfter(limiteCancelar)) {
            throw new IllegalStateException("Não é possível cancelar um pedido que já passou de 90 dias");
        }
    }

    public Page<PedidoResponseDto> buscarTodosPedidos(Pageable pageable, String status)
    {
        try {
            Page<Pedido> pedidos;
            if (status != null) {
                pedidos = pedidoRepository.findByStatusPedido(StatusPedido.valueOf(status), pageable);
            } else {
                pedidos = pedidoRepository.findAll(pageable);
            }
            return pedidos.map(pedido -> modelMapper.map(pedido, PedidoResponseDto.class));

        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status inválido: " + status);
        }
    }
}
