package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(
            PedidoRepository repository,
            ClienteService clienteService,
            ProdutoService produtoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    @Transactional
    public Pedido create(Pedido novoPedido) {
        if (novoPedido.getId() != null) {
            throw new DataIntegrityViolationException(
                    "Não é possível criar um novo pedido quando tem um id informado");
        }

        if (!novoPedido.isRetira() && novoPedido.getEndereco() == null) {
            throw new DataIntegrityViolationException(
                    "Pedido para entregar não possui endereço informado");
        }
        if (novoPedido.isRetira()) {
            novoPedido.setEndereco(null);
        }

        if (novoPedido.isRetira() && novoPedido.getValorEntrega().compareTo(BigDecimal.ZERO) > 0) {
            throw new DataIntegrityViolationException(
                    "Pedido para retirar não pode cobrar valor de entrega");
        }

        if (novoPedido.getItens() == null || novoPedido.getItens().isEmpty()) {
            throw new DataIntegrityViolationException(
                    "Pedido não possui itens");
        }

        Cliente cliente = clienteService.getById(novoPedido.getCliente().getId());

        novoPedido.getItens().forEach(item -> {
            this.validaProdutoEValoresItem(item);
            item.getId().setPedido(novoPedido);
        });

        novoPedido.setCliente(cliente);
        novoPedido.setStatus(PedidoStatus.NOVO);
        novoPedido.setDataHoraPedido(Instant.now());

        return repository.save(novoPedido);
    }

    private void validaProdutoEValoresItem(ItemPedido item) {
        Produto produto = produtoService.getById(item.getId().getProduto().getId());

        if (produto.isDisabled()) {
            throw new DataIntegrityViolationException("Produto [" + produto.getNome() + "] desativado");
        }

        BigDecimal valorOriginalProduto = produto.getValorOriginal();
        BigDecimal valorDescontoProduto = produto.getValorDesconto();
        BigDecimal valorUnitarioItem = item.getValorUnitarioOriginal();
        BigDecimal valorDescontoItem = item.getValorUnitarioDesconto();

        if (!valorOriginalProduto.stripTrailingZeros().equals(valorUnitarioItem.stripTrailingZeros())) {
            throw new DataIntegrityViolationException(
                    "O valor unitário original do produto [" + produto.getNome() +
                            "] está divergente entre pedido e cadastro do produto");
        }

        if (!valorDescontoProduto.stripTrailingZeros().equals(valorDescontoItem.stripTrailingZeros())) {
            throw new DataIntegrityViolationException(
                    "O valor unitário de desconto do produto [" + produto.getNome() +
                            "] está divergente entre pedido e cadastro do produto");
        }

        item.getId().setProduto(produto);
    }

    public List<Pedido> getAll() {
        return repository.findAll();
    }

    public Pedido getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    }

    public List<Pedido> getAllByStatus(PedidoStatus status) {
        return repository.findAllByStatus(status);
    }

    public List<Pedido> getAllByClienteId(Long clienteId) {
        return repository.findAllByClienteId(clienteId);
    }

    @Transactional
    public void updateStatus(Long id, PedidoStatus status) {
        Pedido pedido = getById(id);

        if (status.equals(PedidoStatus.FINALIZADO)
                && !pedido.getStatus().equals(PedidoStatus.EM_ENTREGA)) {
            throw new DataIntegrityViolationException("Pedido precisa estar em entrega para ser finalizado");
        }

        if (status.equals(PedidoStatus.EM_ENTREGA)
                && !pedido.getStatus().equals(PedidoStatus.EM_PREPARO)) {
            throw new DataIntegrityViolationException("Pedido precisa estar em preparo para ser entregue");
        }

        if (status.equals(PedidoStatus.EM_PREPARO)
                && !pedido.getStatus().equals(PedidoStatus.CONFIRMADO)) {
            throw new DataIntegrityViolationException("Pedido precisa estar confirmado para ser preparado");
        }

        if (status.equals(PedidoStatus.CONFIRMADO)
                && !pedido.getStatus().equals(PedidoStatus.NOVO)) {
            throw new DataIntegrityViolationException("Pedido precisa estar novo para ser confirmado");
        }

        repository.updateStatus(id, status.name());
    }


}
