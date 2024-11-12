package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ClienteRepository;
import br.dev.zancanela.quickcup_api.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final PedidoRepository pedidoRepository;

    public ClienteService(
            ClienteRepository repository,
            PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Cliente create(Cliente novoCliente) {
        Cliente cliente;
        try {
            cliente = getByTelefone(novoCliente.getTelefone());
        } catch (EntityNotFoundException e) {
            cliente = new Cliente();
        }

        cliente.setNome(novoCliente.getNome());
        cliente.setTelefone(novoCliente.getTelefone());

        return repository.save(cliente);
    }

    public Cliente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    public Cliente getByTelefone(String telefone) {
        return repository.findByTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Cliente update(Long id, Cliente novoCliente) {
        Cliente cliente = this.getById(id);

        cliente.setNome(novoCliente.getNome());
        cliente.setTelefone(novoCliente.getTelefone());

        return repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = getById(id);

        List<Pedido> pedidos = pedidoRepository.findAllByClienteId(id);
        if (!pedidos.isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui pedidos");
        }

        repository.delete(cliente);
    }


}
