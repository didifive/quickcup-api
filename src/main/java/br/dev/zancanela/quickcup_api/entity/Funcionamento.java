package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "Funcionamento")
public class Funcionamento {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Column(name = "hora_inicio")
    private Time horaInicio;

    @Column(name = "hora_fim")
    private Time horaFim;

    public Funcionamento(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Funcionamento() {}

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }
}
