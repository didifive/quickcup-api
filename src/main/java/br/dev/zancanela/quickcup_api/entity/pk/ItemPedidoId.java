package br.dev.zancanela.quickcup_api.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemPedidoId implements Serializable {

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "produto_id")
    private Long produtoId;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoId that = (ItemPedidoId) o;
        return Objects.equals(getPedidoId(), that.getPedidoId()) && Objects.equals(getProdutoId(), that.getProdutoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPedidoId(), getProdutoId());
    }
}
