package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.instantToStringDateTime;
import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.stringDateTimeToInstant;

public record FuncionamentoEspecialRequest(
        Long id,
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        @NotNull(message = "O tipo deve ser preenchido!")
        FuncionamentoEspecialTipo tipo,
        @NotEmpty(message = "A data de inicio deve ser preenchida!")
        String dataInicio,
        @NotEmpty(message = "A data para fim deve ser preenchida!")
        String dataFim
) {

    public static FuncionamentoEspecialRequest fromEntity(FuncionamentoEspecial entity) {
        return new FuncionamentoEspecialRequest(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                instantToStringDateTime(entity.getDataInicio()),
                instantToStringDateTime(entity.getDataFim())
        );
    }

    public FuncionamentoEspecial toEntity() {
        FuncionamentoEspecial entity = new FuncionamentoEspecial();
        entity.setId(id());
        entity.setNome(nome());
        entity.setTipo(tipo());
        entity.setDataInicio(stringDateTimeToInstant(dataInicio()));
        entity.setDataFim(stringDateTimeToInstant(dataFim()));
        return entity;
    }


}
