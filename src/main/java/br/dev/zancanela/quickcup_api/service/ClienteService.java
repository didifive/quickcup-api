package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Endereco;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoService enderecoService;
    private final PedidoService pedidoService;

    public ClienteService(
            ClienteRepository repository,
            EnderecoService enderecoService,
            PedidoService pedidoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.pedidoService = pedidoService;
    }

    @Transactional
    public Cliente create(Cliente novoCliente) {
        Cliente cliente = repository.findByTelefone(novoCliente.getTelefone())
                .orElse(new Cliente());

        cliente.setNome(novoCliente.getNome());
        cliente.setEmail(novoCliente.getEmail());
        cliente.setTelefone(novoCliente.getTelefone());

        return repository.save(novoCliente);
    }

    public Cliente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Cliente update(Long id, Cliente novoCliente) {
        Cliente cliente = this.getById(id);

        cliente.setNome(novoCliente.getNome());
        cliente.setEmail(novoCliente.getEmail());
        cliente.setTelefone(novoCliente.getTelefone());

        return repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = getById(id);

        List<Pedido> pedidos = pedidoService.getAllByClienteId(id);
        if (!pedidos.isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui pedidos");
        }

        List<Endereco> enderecos = enderecoService.findByClienteId(id);

        enderecos.forEach(e -> enderecoService.delete(e.getId()));

        repository.delete(cliente);
    }


}
