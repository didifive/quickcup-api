package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Pedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        Long clienteId,
        BigDecimal valorOriginal,
        BigDecimal valorDesconto,
        BigDecimal valorEntrega,
        BigDecimal total,
        List<ItemPedidoResponse> itens
) {
    public static PedidoResponse fromEntity(Pedido entity) {
        return new PedidoResponse(
                entity.getCliente().getId(),
                entity.getValorOriginal(),
                entity.getValorDesconto(),
                entity.getValorEntrega(),
                entity.getTotal(),
                entity.getItens().stream().map(ItemPedidoResponse::fromEntity).toList()
        );
    }
}
