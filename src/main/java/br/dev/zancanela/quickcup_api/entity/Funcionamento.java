package br.dev.zancanela.quickcup_api.entity;

import br.dev.zancanela.quickcup_api.entity.abstracts.BasicEntity;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "Funcionamento")
public class Funcionamento extends BasicEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "diaSemana")
    private DiaSemana diaSemana;

    @Column(name = "horaInicio")
    private Time horaInicio;

    @Column(name = "horaFim")
    private Time horaFim;

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
