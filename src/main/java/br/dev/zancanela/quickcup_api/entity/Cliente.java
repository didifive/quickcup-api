package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente extends BasicEntity {
    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "cliente",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(Long id) {
        super.setId(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
