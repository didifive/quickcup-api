package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    private final GrupoService grupoService;
    private final ItemPedidoService itemPedidoService;

    public ProdutoService(
            ProdutoRepository repository,
            GrupoService grupoService,
            ItemPedidoService itemPedidoService) {
        this.repository = repository;
        this.grupoService = grupoService;
        this.itemPedidoService = itemPedidoService;
    }

    @Transactional
    public Produto create(Produto novoProduto) {
        Grupo grupo = grupoService.getById(novoProduto.getGrupo().getId());

        if (novoProduto.getValorDesconto().compareTo(novoProduto.getValorOriginal()) > 0) {
            throw new DataIntegrityViolationException("O valor de desconto deve ser menor que o valor original");
        }

        novoProduto.setGrupo(grupo);

        return repository.save(novoProduto);
    }

    public Produto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    public Produto getByIdAndEnabled(Long id) {
        return repository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto ativo não encontrado"));
    }

    public List<Produto> getAll() { return repository.findAll(); }

    public List<Produto> getAllEnabled() { return repository.findAllByEnabledTrue(); }

    public List<Produto> getAllByGrupoId(Long id) {
        return repository.findAllByGrupoId(id);
    }

    public List<Produto> getAllByGrupoIdAndEnabled(Long id) {
        return repository.findAllByGrupoIdAndEnabledTrue(id);
    }

    @Transactional
    public Produto update(Long id, Produto novoProduto) {
        Produto existente = this.getById(id);

        Grupo grupo = grupoService.getById(novoProduto.getGrupo().getId());

        existente.setNome(novoProduto.getNome());
        existente.setDescricao(novoProduto.getDescricao());
        existente.setValorOriginal(novoProduto.getValorOriginal());
        existente.setValorDesconto(novoProduto.getValorDesconto());
        existente.setCodigo(novoProduto.getCodigo());
        existente.setEnabled(novoProduto.isEnabled());
        existente.setGrupo(grupo);

        return repository.save(existente);
    }

    @Transactional
    public Produto enableProduto(Long id) {
        Produto produto = this.getById(id);

        produto.setEnabled(true);

        return repository.save(produto);
    }

    @Transactional
    public Produto disableProduto(Long id) {
        Produto produto = this.getById(id);

        produto.setEnabled(false);

        return repository.save(produto);
    }

    @Transactional
    public void delete(Long id) {
        Produto existente = this.getById(id);

        List<ItemPedido> itensPedidos = itemPedidoService.getAllByProdutoId(id);
        if (!itensPedidos.isEmpty()) {
            throw new DataIntegrityViolationException("Este produto possui pedidos e não pode ser apagado");
        }

        repository.delete(existente);
    }

}
