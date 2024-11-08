package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    private final GrupoRepository repository;

    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Grupo create(Grupo novoGrupo) {
        validarNomeGrupo(null, novoGrupo.getNome());

        return repository.save(novoGrupo);
    }

    public Grupo getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
    }

    @Transactional
    public Grupo update(Long id, Grupo novoGrupo) {
        Grupo existente = this.getById(id);

        validarNomeGrupo(existente.getNome(), novoGrupo.getNome());

        existente.setNome(novoGrupo.getNome());
        existente.setDescricao(novoGrupo.getDescricao());

        return repository.save(existente);
    }

    public List<Grupo> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        Grupo existente = this.getById(id);

        if (!existente.getProdutos().isEmpty()) {
            throw new DataIntegrityViolationException("Grupo possui produtos cadastrados");
        }

        repository.deleteById(id);
    }

    private void validarNomeGrupo(String existente, String novoNome) {
        if (novoNome.equals(existente)) {
            return;
        }

        if (novoNome.isBlank()) {
            throw new DataIntegrityViolationException("Nome do grupo não pode ser vazio");
        }

        if (repository.findByNome(novoNome).isPresent()) {
            throw new DataIntegrityViolationException(
                    "Já existe um grupo o nome [" +
                            novoNome +
                            "]");
        }
    }

}
