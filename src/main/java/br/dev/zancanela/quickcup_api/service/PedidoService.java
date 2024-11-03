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
        Cliente cliente = clienteService.getById(novoPedido.getCliente().getId());

        BigDecimal total = new BigDecimal(0);

        for (ItemPedido item : novoPedido.getItens()) {
            Produto produto = produtoService.getById(item.getProduto().getId());
            if (produto.isDisabled()) {
                throw new DataIntegrityViolationException("Produto ["+ produto.getNome() +"] desativado");
            }
            BigDecimal totalItem = item.getProduto().getPreco()
                    .multiply(new BigDecimal(item.getQuantidade()));
            total = total.add(totalItem);

        }

        novoPedido.setDataHora(Instant.now());
        novoPedido.setCliente(cliente);
        novoPedido.setStatus(PedidoStatus.NOVO);
        novoPedido.setTotal(total);

        return repository.save(novoPedido);
    }

    public Pedido getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    }

    public List<Pedido> getAllByStatus(PedidoStatus status) {
        return repository.findAllByStatus(status);
    }

    public List<Pedido> getAllByClienteId(Long clienteId) {
        return repository.findAllByCliendId(clienteId);
    }

    @Transactional
    public Pedido updateStatus(Long id, PedidoStatus status) {
        Pedido pedido = getById(id);
        pedido.setStatus(status);

        return repository.save(pedido);
    }


}
