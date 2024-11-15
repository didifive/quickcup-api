package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Item_Pedido")
public class ItemPedido implements Serializable {

    @EmbeddedId
    private ItemPedidoId id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_unitario_original")
    private BigDecimal valorUnitarioOriginal;

    @Column(name = "valor_unitario_desconto")
    private BigDecimal valorUnitarioDesconto;

    public ItemPedidoId getId() {
        return id;
    }

    public void setId(ItemPedidoId id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitarioOriginal() {
        return valorUnitarioOriginal;
    }

    public void setValorUnitarioOriginal(BigDecimal valorUnitarioOriginal) {
        this.valorUnitarioOriginal = valorUnitarioOriginal;
    }

    public BigDecimal getValorUnitarioDesconto() {
        return valorUnitarioDesconto;
    }

    public void setValorUnitarioDesconto(BigDecimal valorUnitarioDesconto) {
        this.valorUnitarioDesconto = valorUnitarioDesconto;
    }
}
