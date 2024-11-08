package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import jakarta.validation.constraints.NotEmpty;

public record GrupoRequest(
        Long id,
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        String descricao
) {

    public static GrupoRequest fromEntity(Grupo entity) {
        return new GrupoRequest(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao()
        );
    }

    public Grupo toEntity() {
        Grupo entity = new Grupo();
        entity.setId(id());
        entity.setNome(nome());
        entity.setDescricao(descricao());
        return entity;
    }
}
