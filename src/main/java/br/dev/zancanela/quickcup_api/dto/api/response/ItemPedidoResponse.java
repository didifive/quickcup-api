package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long produtoId,
        Integer quantidade,
        BigDecimal valorUnitarioOriginal,
        BigDecimal valorUnitarioDesconto
) {
    public static ItemPedidoResponse fromEntity(ItemPedido entity) {
        return new ItemPedidoResponse(
                entity.getProduto().getId(),
                entity.getQuantidade(),
                entity.getValorUnitarioOriginal(),
                entity.getValorUnitarioDesconto()
        );
    }
}
