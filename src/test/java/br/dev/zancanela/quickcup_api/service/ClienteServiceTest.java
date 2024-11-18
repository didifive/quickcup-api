package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ClienteRepository;
import br.dev.zancanela.quickcup_api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClienteNew() {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Nome Teste");
        novoCliente.setTelefone("123456789");

        when(clienteRepository.findByTelefone(any())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(novoCliente);

        Cliente createdCliente = clienteService.create(novoCliente);

        assertThat(createdCliente).isNotNull();
        assertThat(createdCliente.getNome()).isEqualTo(novoCliente.getNome());
        assertThat(createdCliente.getTelefone()).isEqualTo(novoCliente.getTelefone());
    }

    @Test
    void testCreateClienteExisting() {
        Cliente existingCliente = new Cliente();
        existingCliente.setNome("Nome Existente");
        existingCliente.setTelefone("123456789");

        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Nome Atualizado");
        novoCliente.setTelefone("123456789");

        when(clienteRepository.findByTelefone(any())).thenReturn(Optional.of(existingCliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existingCliente);

        Cliente updatedCliente = clienteService.create(novoCliente);

        assertThat(updatedCliente).isNotNull();
        assertThat(updatedCliente.getNome()).isEqualTo(novoCliente.getNome());
        assertThat(updatedCliente.getTelefone()).isEqualTo(novoCliente.getTelefone());
    }

    @Test
    void testGetByIdFound() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome Teste");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.getById(1L);

        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getId()).isEqualTo(1L);
        assertThat(foundCliente.getNome()).isEqualTo("Nome Teste");
    }

    @Test
    void testGetByIdNotFound() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não encontrado");
    }

    @Test
    void testGetByTelefoneFound() {
        Cliente cliente = new Cliente();
        cliente.setTelefone("123456789");

        when(clienteRepository.findByTelefone(any())).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.getByTelefone("123456789");

        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getTelefone()).isEqualTo("123456789");
    }

    @Test
    void testGetByTelefoneNotFound() {
        when(clienteRepository.findByTelefone(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getByTelefone("123456789"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não encontrado");
    }

    @Test
    void testGetAll() {
        List<Cliente> clientes = Collections.singletonList(new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> foundClientes = clienteService.getAll();

        assertThat(foundClientes).isNotEmpty();
    }

    @Test
    void testUpdateCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome Antigo");
        cliente.setTelefone("123456789");

        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Nome Atualizado");
        novoCliente.setTelefone("987654321");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updatedCliente = clienteService.update(1L, novoCliente);

        assertThat(updatedCliente).isNotNull();
        assertThat(updatedCliente.getNome()).isEqualTo(novoCliente.getNome());
        assertThat(updatedCliente.getTelefone()).isEqualTo(novoCliente.getTelefone());
    }

    @Test
    void testDeleteClienteWithoutPedidos() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findAllByClienteId(anyLong())).thenReturn(Collections.emptyList());

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void testDeleteClienteWithPedidos() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Pedido pedido = new Pedido();

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(pedidoRepository.findAllByClienteId(anyLong())).thenReturn(Collections.singletonList(pedido));

        assertThatThrownBy(() -> clienteService.delete(1L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Cliente possui pedidos");
    }
}
