package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Item_Pedido")
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoId id;

    @ManyToOne
    @MapsId("pedidoId")
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor")
    private BigDecimal valor;

    public ItemPedidoId getId() {
        return id;
    }

    public void setId(ItemPedidoId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
