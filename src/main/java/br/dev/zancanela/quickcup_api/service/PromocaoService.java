package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.entity.Promocao;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.PromocaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PromocaoService {

    private final PromocaoRepository repository;
    private final ProdutoService produtoService;

    public PromocaoService(
            PromocaoRepository repository,
            ProdutoService produtoService) {
        this.repository = repository;
        this.produtoService = produtoService;
    }

    @Transactional
    public Promocao create(Promocao novaPromocao) {
        Produto produto = produtoService.getById(novaPromocao.getProduto().getId());

        novaPromocao.setProduto(produto);

        return repository.save(novaPromocao);
    }

    public Promocao getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promocao não encontrada"));
    }

    public List<Promocao> getAllByProdutoId(Long id) {
        return repository.findAllByProdutoId(id);

    }

    public List<Promocao> getAll() {
        return repository.findAll();
    }

    public Optional<Promocao> getPromocaoMaisRelevantePorProdutoId(Long id) {
        List<Promocao> promocoes = getAllByProdutoId(id);

        return promocoes.stream()
                .max(Comparator.comparing(Promocao::getDesconto));

    }

    @Transactional
    public void delete(Long id) {
        Promocao promocao = this.getById(id);
        repository.delete(promocao);
    }

}
