package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.MetodoDePagamento;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PedidoConstants {
    public static final Produto PRODUTO = new Produto(null, "Produto 1", "Descrição do produto 1", BigDecimal.TEN);
    public static final Produto PRODUTO2 = new Produto(null, "Produto 2", "Descrição do Produto 2", BigDecimal.TEN);

    public static final List<Produto> PRODUTO_LIST = Arrays.asList(PRODUTO, PRODUTO2);

    public static final Endereco ENDERECO = new Endereco(
            null,
            10,
            "01310930",
            "2100",
            "SP",
            "São Paulo",
            "Avenida Paulista"
    );

    public static final LocalDateTime TIME_NOW = LocalDateTime.now();
    public static final Pedido PEDIDO = new Pedido(
            null, // Id
            PRODUTO_LIST, // Produtos
            MetodoDePagamento.PIX, //Método de pagamento
            ENDERECO, // Endereço
            BigDecimal.TEN, // Subtotal
            BigDecimal.ONE, // Desconto
            BigDecimal.TEN, // ValorTotal
            TIME_NOW, // Data de criação
            StatusPedido.CONFIRMADO, // Status do pedido
            "", // Motivo cancelamento
            TIME_NOW // Data cancelamento
    );
}
