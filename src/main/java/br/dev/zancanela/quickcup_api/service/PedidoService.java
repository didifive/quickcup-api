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
                    "Não foi possível criar um novo pedido com o id informado");
        }

        if (!novoPedido.isRetira() && novoPedido.getEndereco() == null) {
            throw new DataIntegrityViolationException(
                    "Pedido para entregar não possui endereço informado");
        }

        if (novoPedido.isRetira() && novoPedido.getValorEntrega().compareTo(BigDecimal.ZERO) > 0) {
            throw new DataIntegrityViolationException(
                    "Pedido para retirar não pode cobrar valor de entrega");
        }

        Cliente cliente = clienteService.getById(novoPedido.getCliente().getId());

        BigDecimal totalOriginalCalculado = BigDecimal.ZERO;
        BigDecimal totalDescontoCalculado = BigDecimal.ZERO;
        for (ItemPedido item : novoPedido.getItens()) {
            this.validaProdutoEValoresItem(item);
            BigDecimal totalOriginalItem = item.getValorUnitarioOriginal()
                    .multiply(new BigDecimal(item.getQuantidade()));
            BigDecimal totalDescontoItem = item.getValorUnitarioDesconto()
                    .multiply(new BigDecimal(item.getQuantidade()));
            totalOriginalCalculado = totalOriginalCalculado.add(totalOriginalItem);
            totalDescontoCalculado = totalDescontoCalculado.add(totalDescontoItem);
            item.setPedido(novoPedido);
        }

        if (!novoPedido.getValorOriginal().equals(totalOriginalCalculado)) {
            throw new DataIntegrityViolationException(
                    "O valor original informado para o pedido está divergente com o valor calculado");
        }

        if (!novoPedido.getValorDesconto().equals(totalDescontoCalculado)) {
            throw new DataIntegrityViolationException(
                    "O valor desconto informado para o pedido está divergente com o valor calculado");
        }

        BigDecimal divergenciaTotal = totalOriginalCalculado
                .subtract(totalDescontoCalculado).abs()
                .subtract(novoPedido.getValorEntrega())
                .subtract(novoPedido.getTotal());

        if (!divergenciaTotal.equals(BigDecimal.ZERO)) {
            throw new DataIntegrityViolationException(
                    "O valor total do pedido está divergente com valor original, de desconto e entrega");
        }

        novoPedido.setCliente(cliente);
        novoPedido.setStatus(PedidoStatus.NOVO);
        novoPedido.setDataHoraPedido(Instant.now());

        return repository.save(novoPedido);
    }

    private void validaProdutoEValoresItem(ItemPedido item) {
        Produto produto = produtoService.getById(item.getProduto().getId());

        if (produto.isDisabled()) {
            throw new DataIntegrityViolationException("Produto [" + produto.getNome() + "] desativado");
        }

        BigDecimal valorOriginalProduto = produto.getValorOriginal();
        BigDecimal valorDescontoProduto = produto.getValorDesconto();
        BigDecimal valorUnitarioOriginal = item.getValorUnitarioOriginal();
        BigDecimal valorUnitarioDesconto = item.getValorUnitarioDesconto();
        BigDecimal valorUnitarioPedido = item.getValorUnitario();

        if (!valorOriginalProduto.equals(valorUnitarioOriginal)) {
            throw new DataIntegrityViolationException(
                    "O valor unitário original do produto [" + produto.getNome() +
                            "] está divergente entre pedido e cadastro do produto");
        }

        if (!valorDescontoProduto.equals(valorUnitarioDesconto)) {
            throw new DataIntegrityViolationException(
                    "O valor unitário de desconto do produto [" + produto.getNome() +
                            "] está divergente entre pedido e cadastro do produto");
        }

        if (!valorUnitarioOriginal.subtract(valorUnitarioDesconto).equals(valorUnitarioPedido)) {
            throw new DataIntegrityViolationException(
                    "O valor unitário do produto [" + produto.getNome() +
                            "] está divergente com valor original menos o desconto");
        }

        item.setProduto(produto);
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
    public Pedido updateStatus(Long id, PedidoStatus status) {
        Pedido pedido = getById(id);
        pedido.setStatus(status);

        return repository.save(pedido);
    }


}
