package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Endereco;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Endereco create(Endereco novoEndereco) {
        return repository.save(novoEndereco);
    }

    public Endereco getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }

    public List<Endereco> findByClienteId(Long id) {
        return repository.findByClienteId(id);
    }

    @Transactional
    public Endereco update(Long id, Endereco novoEndereco) {
        Endereco endereco = this.getById(id);

        endereco.setCep(novoEndereco.getCep());
        endereco.setLogradouro(novoEndereco.getLogradouro());
        endereco.setNumero(novoEndereco.getNumero());
        endereco.setComplemento(novoEndereco.getComplemento());
        endereco.setBairro(novoEndereco.getBairro());
        endereco.setCidade(novoEndereco.getCidade());
        endereco.setEstado(novoEndereco.getEstado());
        endereco.setLongitude(novoEndereco.getLongitude());
        endereco.setLatitude(novoEndereco.getLatitude());
        endereco.setCliente(novoEndereco.getCliente());

        return repository.save(endereco);
    }

    @Transactional
    public void delete(Long id) {
        Endereco endereco = this.getById(id);
        repository.delete(endereco);
    }
}
