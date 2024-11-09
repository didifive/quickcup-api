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

import static br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus.NOVO;

@Service
public class PedidoService {

    private static final BigDecimal DIVERGENCIA_ACEITAVEL = BigDecimal.ZERO;
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
        Cliente cliente = clienteService.getById(novoPedido.getCliente().getId());

        if (novoPedido.getId() != null) {
            throw new DataIntegrityViolationException(
                    "Não foi possivel criar um novo pedido com o id informado");
        }

        BigDecimal totalOriginal = new BigDecimal(0);
        BigDecimal totalDesconto = new BigDecimal(0);
        BigDecimal total = novoPedido.getTotal();

        for (ItemPedido item : novoPedido.getItens()) {
            this.validaProdutoEValores(item);

            BigDecimal totalOriginalItem = item.getValorUnitarioOriginal()
                    .multiply(new BigDecimal(item.getQuantidade()));

            BigDecimal totalDescontoItem = item.getValorUnitarioDesconto()
                    .multiply(new BigDecimal(item.getQuantidade()));

            totalOriginal = totalOriginal.add(totalOriginalItem);
            totalDesconto = totalDesconto.add(totalDescontoItem);

        }

        BigDecimal divergenciaTotal = totalOriginal.subtract(totalDesconto).abs().subtract(total);

        if (divergenciaTotal.compareTo(DIVERGENCIA_ACEITAVEL) > 0) {
            throw new DataIntegrityViolationException(
                    "O valor total do pedido esta divergente com valor original e desconto" +
                            " mesmo considerando divergencia aceitável de: "
                            + DIVERGENCIA_ACEITAVEL);
        }

        novoPedido.setCliente(cliente);
        novoPedido.setStatus(NOVO);
        novoPedido.setValorOriginal(totalOriginal);
        novoPedido.setValorDesconto(totalDesconto);
        novoPedido.setDataHora(Instant.now());

        return repository.save(novoPedido);
    }

    private void validaProdutoEValores(ItemPedido item) {
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

        if (valorDescontoProduto.compareTo(valorUnitarioOriginal) > 0) {
            throw new DataIntegrityViolationException(
                    "O valor unitário de desconto do produto [" + produto.getNome() +
                            "] está divergente entre pedido e cadastro do produto");
        }

        if (!valorUnitarioOriginal.subtract(valorUnitarioDesconto).equals(valorUnitarioPedido)) {
            throw new DataIntegrityViolationException(
                    "O valor unitário do produto [" + produto.getNome() +
                            "] está divergente com valor original menos o desconto");
        }
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
