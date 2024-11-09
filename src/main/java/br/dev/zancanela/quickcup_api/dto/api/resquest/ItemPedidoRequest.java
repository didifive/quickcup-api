package br.dev.zancanela.quickcup_api.dto.api.resquest;

import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoRequest(
        @NotNull(message = "O id do produto deve ser preenchido!")
        Long produtoId,
        @NotNull(message = "A quantidade deve ser preenchida!")
        @Min(value = 1, message = "A quantidade deve ser igual ou maior que 1")
        Integer quantidade,
        @NotNull(message = "O valor original do produto deve ser preenchido!")
        BigDecimal valorUnitarioOriginal,
        @NotNull(message = "O valor unitario de desconto deve ser preenchido!")
        BigDecimal valorUnitarioDesconto,
        @NotNull(message = "O valor unitario deve ser preenchido!")
        BigDecimal valorUnitario
) {
    public ItemPedido toEntity() {
        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setProduto(new Produto(produtoId()));
        itemPedido.setQuantidade(quantidade());
        itemPedido.setValorUnitarioOriginal(valorUnitarioOriginal());
        itemPedido.setValorUnitarioDesconto(valorUnitarioDesconto());
        itemPedido.setValorUnitario(valorUnitario());

        return itemPedido;
    }
}
