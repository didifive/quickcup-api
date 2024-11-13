package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.enums.FormaPagamento;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        Long clienteId,
        PedidoStatus status,
        BigDecimal valorEntrega,
        boolean retira,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String endereco,
        FormaPagamento formaPagamento,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String observacoes,
        List<ItemPedidoResponse> itens
) {
    public static PedidoResponse fromEntity(Pedido entity) {
        return new PedidoResponse(
                entity.getCliente().getId(),
                entity.getStatus(),
                entity.getValorEntrega(),
                entity.isRetira(),
                entity.getEndereco(),
                entity.getFormaPagamento(),
                entity.getObservacoes(),
                entity.getItens().stream().map(ItemPedidoResponse::fromEntity).toList()
        );
    }
}
