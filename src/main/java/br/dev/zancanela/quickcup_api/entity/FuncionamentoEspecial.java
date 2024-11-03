package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import br.dev.zancanela.quickcup_api.entity.enums.FunctiomentoEspecialTipo;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "Funcionamento_Especial")
public class FuncionamentoEspecial extends BasicEntity {

    @Column(name="nome")
    private String nome;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private FunctiomentoEspecialTipo tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public FunctiomentoEspecialTipo getTipo() {
        return tipo;
    }

    public void setTipo(FunctiomentoEspecialTipo tipo) {
        this.tipo = tipo;
    }
}
