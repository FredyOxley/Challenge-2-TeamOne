package com.compassuol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Endereco;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.MetodoDePagamento;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.enums.StatusPedido;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.EnderecoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.repository.PedidoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.pedido.service.PedidoService;
import com.compassuol.sp.challenge.ecommerce.domain.produto.entity.Produto;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.BadRequestException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.exception.EntityNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.produto.repository.ProdutoRepository;
import com.compassuol.sp.challenge.ecommerce.domain.produto.service.ProdutoService;
import com.compassuol.sp.challenge.ecommerce.web.client.ViaCepClient;
import com.compassuol.sp.challenge.ecommerce.web.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PedidoRepository pedidoRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void buscarEnderecoPorCep_WithValidCep_ReturnsEndereco() {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("12345678");
        enderecoDto.setNumero(Integer.valueOf("123"));

        ViaCepClientDto viaCepClientDto = new ViaCepClientDto();
        viaCepClientDto.setCep("12345678");
        viaCepClientDto.setLocalidade("Test City");

        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setNumeroEndereco(Integer.valueOf("123"));
        endereco.setCidade("Test City");

        when(viaCepClient.findByCep(any())).thenReturn(viaCepClientDto);
        when(modelMapper.map(any(), any())).thenReturn(endereco);
        when(enderecoRepository.save(any())).thenReturn(endereco);

        pedidoService.buscarEnderecoPorCep(enderecoDto);

        verify(viaCepClient).findByCep(any());
        verify(modelMapper).map(any(), any());
        verify(enderecoRepository).save(any());
    }

    @Test
    public void buscarEnderecoPorCep_WithInvalidCep_ThrowsException() {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("invalid");
        enderecoDto.setNumero(Integer.valueOf("123"));

        when(viaCepClient.findByCep(any())).thenThrow(new RuntimeException("Invalid CEP"));

        assertThrows(RuntimeException.class, () -> pedidoService.buscarEnderecoPorCep(enderecoDto));

        verify(viaCepClient).findByCep(any());
    }

    @Test
    public void salvar_WithInvalidData_ThrowsException() {
        PedidoCreateDto pedidoCreateDto = new PedidoCreateDto();
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("invalid");
        enderecoDto.setNumero(123);
        pedidoCreateDto.setEndereco(enderecoDto);

        when(viaCepClient.findByCep(any())).thenThrow(new RuntimeException("Invalid CEP"));

        assertThrows(RuntimeException.class, () -> pedidoService.salvar(pedidoCreateDto));
    }


    @Test
    public void buscarPorId_WithExistingId_ReturnsPedido() {
        Long id = 1L;
        Pedido expectedPedido = new Pedido();
        expectedPedido.setId(id);

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(expectedPedido));

        Pedido actualPedido = pedidoService.buscarPorId(id);

        assertEquals(expectedPedido, actualPedido);
    }

    @Test
    public void buscarPorId_WithNonExistingId_ThrowsException() {
        Long id = 1L;

        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoService.buscarPorId(id));
    }

    @Test
    public void atualizarStatus_WithConfirmedStatus_ChangesToSent() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatusPedido(StatusPedido.CONFIRMADO);

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any())).thenReturn(pedido);

        Pedido updatedPedido = pedidoService.atualizarStatus(id);

        assertEquals(StatusPedido.ENVIADO, updatedPedido.getStatusPedido());
    }

    @Test
    public void atualizarStatus_WithNonConfirmedStatus_ThrowsException() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatusPedido(StatusPedido.ENVIADO);

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));

        assertThrows(IllegalStateException.class, () -> pedidoService.atualizarStatus(id));
    }


    @Test
    public void cancelarPedido_WithNonExistingId_ThrowsException() {
        Long id = 1L;
        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();
        pedidoCancelDto.setMotivoCancelamento("Test reason");

        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoService.cancelarPedido(id, pedidoCancelDto));
    }

    @Test
    public void cancelarPedido_WithSentStatus_ThrowsException() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatusPedido(StatusPedido.ENVIADO);
        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();
        pedidoCancelDto.setMotivoCancelamento("Test reason");

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));

        assertThrows(IllegalStateException.class, () -> pedidoService.cancelarPedido(id, pedidoCancelDto));
    }

    @Test
    public void cancelarPedido_WithExpiredCancellationPeriod_ThrowsException() {
        Long id = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatusPedido(StatusPedido.CONFIRMADO);
        pedido.setDataCriacao(LocalDateTime.now().minusDays(91));
        PedidoCancelDto pedidoCancelDto = new PedidoCancelDto();
        pedidoCancelDto.setMotivoCancelamento("Test reason");

        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido));

        assertThrows(IllegalStateException.class, () -> pedidoService.cancelarPedido(id, pedidoCancelDto));
    }



    @Test
    public void validarPedidoParaCancelar_WithSentStatus_ThrowsException() {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.ENVIADO);

        assertThrows(IllegalStateException.class, () -> pedidoService.validarPedidoParaCancelar(pedido));
    }

    @Test
    public void validarPedidoParaCancelar_WithExpiredCancellationPeriod_ThrowsException() {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.CONFIRMADO);
        pedido.setDataCriacao(LocalDateTime.now().minusDays(91));

        assertThrows(IllegalStateException.class, () -> pedidoService.validarPedidoParaCancelar(pedido));
    }

    @Test
    public void validarPedidoParaCancelar_WithValidCancellationPeriod_DoesNotThrowException() {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.CONFIRMADO);
        pedido.setDataCriacao(LocalDateTime.now().minusDays(89));

        assertDoesNotThrow(() -> pedidoService.validarPedidoParaCancelar(pedido));
    }

    @Test
    void buscarTodosPedidos_RetornarListaDePedidosComSucesso() {

        Page<Pedido> paginaPedidos = new PageImpl<>(Collections.singletonList(new Pedido()));
        when(pedidoRepository.findAll(any(Pageable.class))).thenReturn(paginaPedidos);

        Page<PedidoResponseDto> resultado = pedidoService.buscarTodosPedidos(Pageable.unpaged(), null);

        assertEquals(paginaPedidos.getTotalElements(), resultado.getTotalElements());
    }

    @Test
    void buscarTodosPedidos_StatusInvalido_DeveLancarBadRequestException() {
        String statusInvalido = "STATUS_INVALIDO";
        BadRequestException excecao = org.junit.jupiter.api.Assertions.assertThrows(
                BadRequestException.class,
                () -> pedidoService.buscarTodosPedidos(Pageable.unpaged(), statusInvalido)
        );

        assertEquals("Status inválido: " + statusInvalido, excecao.getMessage());
    }

}