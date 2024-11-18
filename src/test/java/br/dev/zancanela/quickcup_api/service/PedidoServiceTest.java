package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePedido() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setValorOriginal(BigDecimal.valueOf(100));
        produto.setValorDesconto(BigDecimal.valueOf(50));
        produto.setEnabled(true);

        ItemPedido item = new ItemPedido();
        item.setId(new ItemPedidoId());
        item.getId().setProduto(produto);
        item.setValorUnitarioOriginal(BigDecimal.valueOf(100));
        item.setValorUnitarioDesconto(BigDecimal.valueOf(50));

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setItens(List.of(item));
        novoPedido.setRetira(true);
        novoPedido.setEndereco(null);
        novoPedido.setValorEntrega(BigDecimal.ZERO);

        when(clienteService.getById(anyLong())).thenReturn(cliente);
        when(produtoService.getById(anyLong())).thenReturn(produto);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(novoPedido);

        Pedido createdPedido = pedidoService.create(novoPedido);

        assertThat(createdPedido).isNotNull();
        assertThat(createdPedido.getCliente()).isEqualTo(cliente);
        assertThat(createdPedido.getItens().get(0).getId().getProduto()).isEqualTo(produto);
    }

    @Test
    void testCreatePedidoWithId() {
        Pedido novoPedido = new Pedido();
        novoPedido.setId(1L);

        assertThatThrownBy(() -> pedidoService.create(novoPedido))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Não é possível criar um novo pedido quando tem um id informado");
    }

    @Test
    void testCreatePedidoWithoutEndereco() {
        Pedido novoPedido = new Pedido();
        novoPedido.setRetira(false);
        novoPedido.setEndereco(null);

        assertThatThrownBy(() -> pedidoService.create(novoPedido))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Pedido para entregar não possui endereço informado");
    }

    @Test
    void testCreatePedidoWithInvalidEntrega() {
        Pedido novoPedido = new Pedido();
        novoPedido.setRetira(true);
        novoPedido.setValorEntrega(BigDecimal.valueOf(10));

        assertThatThrownBy(() -> pedidoService.create(novoPedido))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Pedido para retirar não pode cobrar valor de entrega");
    }

    @Test
    void testCreatePedidoWithoutItens() {
        Pedido novoPedido = new Pedido();
        novoPedido.setEndereco("endereco_teste");
        novoPedido.setItens(Collections.emptyList());

        assertThatThrownBy(() -> pedidoService.create(novoPedido))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Pedido não possui itens");
    }

    @Test
    void testGetAll() {
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> foundPedidos = pedidoService.getAll();

        assertThat(foundPedidos).isNotEmpty();
    }

    @Test
    void testGetByIdFound() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));

        Pedido foundPedido = pedidoService.getById(1L);

        assertThat(foundPedido).isNotNull();
        assertThat(foundPedido.getId()).isEqualTo(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não encontrado");
    }

    @Test
    void testGetAllByStatus() {
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(pedidoRepository.findAllByStatus(any())).thenReturn(pedidos);

        List<Pedido> foundPedidos = pedidoService.getAllByStatus(PedidoStatus.NOVO);

        assertThat(foundPedidos).isNotEmpty();
    }

    @Test
    void testGetAllByClienteId() {
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(pedidoRepository.findAllByClienteId(anyLong())).thenReturn(pedidos);

        List<Pedido> foundPedidos = pedidoService.getAllByClienteId(1L);

        assertThat(foundPedidos).isNotEmpty();
    }

    @Test
    void testUpdateStatusFinalizado() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.EM_ENTREGA);

        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));

        pedidoService.updateStatus(1L, PedidoStatus.FINALIZADO);

        verify(pedidoRepository, times(1)).updateStatus(1L, PedidoStatus.FINALIZADO.name());
    }

    @Test
    void testUpdateStatusFinalizadoWithInvalidStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.CONFIRMADO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        assertThatThrownBy(() -> pedidoService.updateStatus(1L, PedidoStatus.FINALIZADO)).isInstanceOf(DataIntegrityViolationException.class).hasMessage("Pedido precisa estar em entrega para ser finalizado");
    }

    @Test
    void testUpdateStatusEmEntrega() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.EM_PREPARO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        pedidoService.updateStatus(1L, PedidoStatus.EM_ENTREGA);
        verify(pedidoRepository, times(1)).updateStatus(1L, PedidoStatus.EM_ENTREGA.name());
    }

    @Test
    void testUpdateStatusEmEntregaWithInvalidStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.NOVO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        assertThatThrownBy(() -> pedidoService.updateStatus(1L, PedidoStatus.EM_ENTREGA)).isInstanceOf(DataIntegrityViolationException.class).hasMessage("Pedido precisa estar em preparo para ser entregue");
    }

    @Test
    void testUpdateStatusEmPreparo() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.CONFIRMADO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        pedidoService.updateStatus(1L, PedidoStatus.EM_PREPARO);
        verify(pedidoRepository, times(1)).updateStatus(1L, PedidoStatus.EM_PREPARO.name());
    }

    @Test
    void testUpdateStatusEmPreparoWithInvalidStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.NOVO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        assertThatThrownBy(() -> pedidoService.updateStatus(1L, PedidoStatus.EM_PREPARO)).isInstanceOf(DataIntegrityViolationException.class).hasMessage("Pedido precisa estar confirmado para ser preparado");
    }

    @Test
    void testUpdateStatusConfirmado() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.NOVO);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        pedidoService.updateStatus(1L, PedidoStatus.CONFIRMADO);
        verify(pedidoRepository, times(1)).updateStatus(1L, PedidoStatus.CONFIRMADO.name());
    }

    @Test
    void testUpdateStatusConfirmadoWithInvalidStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(PedidoStatus.EM_PREPARO);

        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));

        assertThatThrownBy(() -> pedidoService.updateStatus(1L, PedidoStatus.CONFIRMADO))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Pedido precisa estar novo para ser confirmado");
    }
}