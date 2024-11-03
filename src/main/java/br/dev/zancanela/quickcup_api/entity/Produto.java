package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Produto")
public class Produto extends BasicEntity {

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !isEnabled();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
