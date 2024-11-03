package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    private final GrupoService grupoService;

    public ProdutoService(
            ProdutoRepository repository,
            GrupoService grupoService) {
        this.repository = repository;
        this.grupoService = grupoService;
    }

    @Transactional
    public Produto create(Produto novoProduto) {
        Grupo grupo = grupoService.getById(novoProduto.getGrupo().getId());

        novoProduto.setGrupo(grupo);

        return repository.save(novoProduto);
    }

    public Produto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    public List<Produto> getAllEnabled() { return repository.findAll(); }

    public List<Produto> getAllByGrupoId(Long id) {
        return repository.findAllByGrupoId(id);
    }

    @Transactional
    public Produto update(Long id, Produto novoProduto) {
        Produto existente = this.getById(id);

        Grupo grupo = grupoService.getById(novoProduto.getGrupo().getId());

        existente.setNome(novoProduto.getNome());
        existente.setDescricao(novoProduto.getDescricao());
        existente.setPreco(novoProduto.getPreco());
        existente.setCodigo(novoProduto.getCodigo());
        existente.setEnabled(novoProduto.isEnabled());
        existente.setGrupo(grupo);

        return repository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Produto existente = this.getById(id);

        repository.delete(existente);
    }

}
