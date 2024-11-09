package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;

import java.sql.Time;

public record FuncionamentoSemanalResponse(
        DiaSemana diaSemana,
        Time horaInicio,
        Time horaFim
) {
    public static FuncionamentoSemanalResponse fromEntity(Funcionamento entity) {
        return new FuncionamentoSemanalResponse(
                entity.getDiaSemana(),
                entity.getHoraInicio(),
                entity.getHoraFim()
        );
    }
}
