package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Pedido")
public class Pedido extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PedidoStatus status;

    @Column(name = "valor_entrega")
    private BigDecimal valorEntrega;

    @Column(name = "retira")
    private boolean retira;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "data_hora_pedido")
    private Instant dataHoraPedido;

    @OneToMany(mappedBy = "pedido"
            , fetch = FetchType.EAGER
            , cascade = {CascadeType.PERSIST})
    private List<ItemPedido> itens;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public BigDecimal getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(BigDecimal valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public boolean isRetira() {
        return retira;
    }

    public void setRetira(boolean retira) {
        this.retira = retira;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Instant getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(Instant dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
    }
}
