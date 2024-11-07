package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.stringTimeToTime;
import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.timeToStringTime;

public record FuncionamentoSemanalRequest(
        @NotNull(message = "O dia da semana deve ser preenchido!")
        DiaSemana diaSemana,
        @NotEmpty
        String horaAbertura,
        @NotEmpty
        String horaFechamento
) {
        public static FuncionamentoSemanalRequest fromEntity(Funcionamento entity) {
                return new FuncionamentoSemanalRequest(
                        entity.getDiaSemana(),
                        timeToStringTime(entity.getHoraInicio()),
                        timeToStringTime(entity.getHoraFim())
                );
        }

        public Funcionamento toEntity() {
                Funcionamento entity = new Funcionamento(diaSemana());
                entity.setHoraInicio(stringTimeToTime(horaAbertura()));
                entity.setHoraFim(stringTimeToTime(horaFechamento()));
                return entity;
        }
}
