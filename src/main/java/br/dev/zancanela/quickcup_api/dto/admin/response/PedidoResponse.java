package br.dev.zancanela.quickcup_api.dto.admin.response;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.enums.FormaPagamento;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PedidoResponse(
        Long id,
        Cliente cliente,
        PedidoStatus status,
        BigDecimal valorEntrega,
        boolean isRetira,
        String endereco,
        FormaPagamento formaPagamento,
        String observacoes,
        Instant dataHoraPedido,
        BigDecimal valorTotalItens,
        List<ItemPedidoResponse> itens
) {
    public static PedidoResponse fromEntity(Pedido entity) {

        List<ItemPedidoResponse> itens = entity.getItens().stream().map(ItemPedidoResponse::fromEntity).toList();

        BigDecimal valorItens = itens.stream().map(ItemPedidoResponse::valorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponse(
                entity.getId(),
                entity.getCliente(),
                entity.getStatus(),
                entity.getValorEntrega(),
                entity.isRetira(),
                entity.getEndereco(),
                entity.getFormaPagamento(),
                entity.getObservacoes(),
                entity.getDataHoraPedido(),
                valorItens,
                entity.getItens().stream().map(ItemPedidoResponse::fromEntity).toList()
        );
    }
}
