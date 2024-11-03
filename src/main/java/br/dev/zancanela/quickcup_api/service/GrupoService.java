package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.GrupoRepository;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {

    private final GrupoRepository repository;

    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    public Grupo create(Grupo grupo) {
        validarNomeGrupo(null, grupo.getNome());

        return repository.save(grupo);
    }

    public Grupo getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
    }

    public Grupo update(Long id, Grupo novoGrupo) {
        Grupo existente = this.getById(id);

        validarNomeGrupo(existente.getNome(), novoGrupo.getNome());

        existente.setNome(novoGrupo.getNome());
        existente.setDescricao(novoGrupo.getDescricao());

        return repository.save(existente);
    }

    private void validarNomeGrupo(String existente, String novoNome) {
        if (novoNome.equals(existente)) {
            return;
        }

        if (novoNome.isBlank()) {
            throw new DataIntegrityViolationException("Nome do grupo não pode ser vazio");
        }

        if (repository.findByNome(existente).isPresent()) {
            throw new DataIntegrityViolationException("Já existe um grupo com esse nome");
        }
    }

}
