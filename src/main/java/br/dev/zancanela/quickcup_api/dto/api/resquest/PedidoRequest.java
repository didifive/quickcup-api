package br.dev.zancanela.quickcup_api.dto.api.resquest;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.enums.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PedidoRequest(
        @NotNull(message = "O id do cliente deve ser preenchido!")
        Long clienteId,
        @NotNull(message = "O valor de entrega deve ser preenchido!")
        BigDecimal valorEntrega,
        boolean retira,
        String endereco,
        FormaPagamento formaPagamento,
        String observacoes,
        @Valid
        @NotEmpty
        @Min(value = 1, message = "O pedido deve ter pelo menos um item!")
        List<ItemPedidoRequest> itens
) {
    public Pedido toEntity() {
        Pedido pedido = new Pedido();

        List<ItemPedido> itens = itens().stream().map(ItemPedidoRequest::toEntity).toList();

        pedido.setCliente(new Cliente(clienteId()));
        pedido.setValorEntrega(valorEntrega());
        pedido.setRetira(retira());
        pedido.setEndereco(endereco());
        pedido.setFormaPagamento(formaPagamento());
        pedido.setObservacoes(observacoes());
        pedido.setItens(itens);

        return pedido;
    }
}
