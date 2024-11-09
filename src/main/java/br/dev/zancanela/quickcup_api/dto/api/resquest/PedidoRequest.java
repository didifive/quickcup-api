package br.dev.zancanela.quickcup_api.dto.api.resquest;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PedidoRequest(
        @NotNull(message = "O id do cliente deve ser preenchido!")
        Long clienteId,
        @NotNull(message = "A soma dos valores originais dos produtos deve ser preenchida!")
        BigDecimal valorOriginal,
        @NotNull(message = "A soma dos valores de desconto dos produtos deve ser preenchida!")
        BigDecimal valorDesconto,
        @NotNull(message = "O valor de entrega deve ser preenchido!")
        BigDecimal valorEntrega,
        @NotNull(message = "O valor total deve ser preenchido!")
        BigDecimal total,
        @Valid
        @NotEmpty
        @Min(value = 1, message = "O pedido deve ter pelo menos um item!")
        List<ItemPedidoRequest> itens
) {
    public Pedido toEntity() {
        Pedido pedido = new Pedido();

        List<ItemPedido> itens = itens().stream().map(ItemPedidoRequest::toEntity).toList();

        pedido.setCliente(new Cliente(clienteId()));
        pedido.setValorOriginal(valorOriginal());
        pedido.setValorDesconto(valorDesconto());
        pedido.setValorEntrega(valorEntrega());
        pedido.setTotal(total());
        pedido.setItens(itens);

        return pedido;
    }
}
