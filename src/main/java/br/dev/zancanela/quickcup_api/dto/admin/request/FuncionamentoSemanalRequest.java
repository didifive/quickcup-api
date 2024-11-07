package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.annotation.validation.ValidEnum;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FuncionamentoSemanalRequest(
        @NotNull(message = "O dia da semana deve ser preenchido!")
        @ValidEnum(enumClass = DiaSemana.class, message = "O dia da semana informado está inválido")
        DiaSemana diaSemana,
        @NotEmpty(message = "A hora de abertura deve ser preenchida!")
        String horaAbertura,
        @NotEmpty(message = "A hora de fechamento deve ser preenchida!")
        String horaFechamento
) {
//        public static FuncionamentoSemanalRequest fromEntity(Funcionamento entity) {
//                return new FuncionamentoSemanalRequest(
//                        entity.getDiaSemana(),
//                        entity.getHoraAbertura(),
//                        entity.getHoraFechamento()
//                );
//        }
//
//        public Funcionamento toEntity() {
//                FuncionamentoSemanal entity = new FuncionamentoSemanal();
//                entity.setDiaSemana(diaSemana());
//                entity.setHoraAbertura(stringToInstant(horaAbertura()));
//                entity.setHoraFechamento(stringToInstant(horaFechamento()));
//                return entity;
//        }
}
