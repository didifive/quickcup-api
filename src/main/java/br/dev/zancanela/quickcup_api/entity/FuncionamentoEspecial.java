package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "Funcionamento_Especial")
public class FuncionamentoEspecial extends BasicEntity implements Comparable<FuncionamentoEspecial> {

    @Column(name="nome")
    private String nome;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private FuncionamentoEspecialTipo tipo;

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

    public FuncionamentoEspecialTipo getTipo() {
        return tipo;
    }

    public void setTipo(FuncionamentoEspecialTipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public int compareTo(FuncionamentoEspecial o) {
        if (this.getDataInicio().isBefore(o.getDataInicio())) {
            return -1;
        }
        if (this.getDataInicio().isAfter(o.getDataInicio())) {
            return 1;
        }
        if (this.getDataFim().isBefore(o.getDataFim())) {
            return -1;
        }
        if (this.getDataFim().isAfter(o.getDataFim())) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncionamentoEspecial that = (FuncionamentoEspecial) o;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getDataInicio(), that.getDataInicio()) && Objects.equals(getDataFim(), that.getDataFim()) && getTipo() == that.getTipo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getDataInicio(), getDataFim(), getTipo());
    }
}
