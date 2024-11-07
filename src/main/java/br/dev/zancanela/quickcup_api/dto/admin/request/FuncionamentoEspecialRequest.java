package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.annotation.validation.ValidEnum;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.instantToString;
import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.stringToInstant;

public record FuncionamentoEspecialRequest(
        Long id,
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        @NotNull(message = "O tipo deve ser preenchido!")
        @ValidEnum(enumClass = FuncionamentoEspecialTipo.class, message = "O tipo informado está inválido")
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
                instantToString(entity.getDataInicio()),
                instantToString(entity.getDataFim())
        );
    }

    public FuncionamentoEspecial toEntity() {
        FuncionamentoEspecial entity = new FuncionamentoEspecial();
        entity.setId(id());
        entity.setNome(nome());
        entity.setTipo(tipo());
        entity.setDataInicio(stringToInstant(dataInicio()));
        entity.setDataFim(stringToInstant(dataFim()));
        return entity;
    }


}
