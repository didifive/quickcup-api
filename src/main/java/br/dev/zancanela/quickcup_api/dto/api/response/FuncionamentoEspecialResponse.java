package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;

import java.time.ZonedDateTime;

import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.instantToZonedDateTime;

public record FuncionamentoEspecialResponse(
        Long id,
        String nome,
        ZonedDateTime dataInicio,
        ZonedDateTime dataFim,
        FuncionamentoEspecialTipo tipo
) {
    public static FuncionamentoEspecialResponse fromEntity(FuncionamentoEspecial entity) {
        return new FuncionamentoEspecialResponse(
                entity.getId(),
                entity.getNome(),
                instantToZonedDateTime(entity.getDataInicio()),
                instantToZonedDateTime(entity.getDataFim()),
                entity.getTipo()
        );
    }
}
