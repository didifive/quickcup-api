package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Endereco;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.EnderecoRepository;
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

class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEndereco() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Endereco novoEndereco = new Endereco();
        novoEndereco.setCliente(cliente);
        novoEndereco.setCep("12345-678");

        when(clienteService.getById(anyLong())).thenReturn(cliente);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(novoEndereco);

        Endereco createdEndereco = enderecoService.create(novoEndereco);

        assertThat(createdEndereco).isNotNull();
        assertThat(createdEndereco.getCep()).isEqualTo("12345-678");
    }

    @Test
    void testGetByIdFound() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep("12345-678");

        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));

        Endereco foundEndereco = enderecoService.getById(1L);

        assertThat(foundEndereco).isNotNull();
        assertThat(foundEndereco.getId()).isEqualTo(1L);
        assertThat(foundEndereco.getCep()).isEqualTo("12345-678");
    }

    @Test
    void testGetByIdNotFound() {
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enderecoService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Endereço não encontrado");
    }

    @Test
    void testGetAllByClienteId() {
        List<Endereco> enderecos = Collections.singletonList(new Endereco());
        when(enderecoRepository.findAllByClienteId(anyLong())).thenReturn(enderecos);

        List<Endereco> foundEnderecos = enderecoService.getAllByClienteId(1L);

        assertThat(foundEnderecos).isNotEmpty();
    }

    @Test
    void testUpdateEndereco() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep("12345-678");
        endereco.setCliente(cliente);

        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep("98765-432");
        novoEndereco.setCliente(cliente);

        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));
        when(clienteService.getById(anyLong())).thenReturn(cliente);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco updatedEndereco = enderecoService.update(1L, novoEndereco);

        assertThat(updatedEndereco).isNotNull();
        assertThat(updatedEndereco.getCep()).isEqualTo("98765-432");
    }

    @Test
    void testDeleteEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);

        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));

        enderecoService.delete(1L);

        verify(enderecoRepository, times(1)).delete(endereco);
    }
}
