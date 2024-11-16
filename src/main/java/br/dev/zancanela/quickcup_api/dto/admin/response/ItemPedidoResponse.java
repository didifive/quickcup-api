package br.dev.zancanela.quickcup_api.dto.admin.response;

import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        ItemPedidoId id,
        Integer quantidade,
        BigDecimal valorUnitarioOriginal,
        BigDecimal valorUnitarioDesconto,
        BigDecimal valorTotal
) {
    public static ItemPedidoResponse fromEntity(ItemPedido entity) {
        BigDecimal valorSemDesconto = entity.getValorUnitarioOriginal()
                .subtract(entity.getValorUnitarioDesconto());

        BigDecimal valorTotal = valorSemDesconto.multiply(new BigDecimal(entity.getQuantidade()));

        return new ItemPedidoResponse(
                entity.getId(),
                entity.getQuantidade(),
                entity.getValorUnitarioOriginal(),
                entity.getValorUnitarioDesconto(),
                valorTotal
        );
    }
}
