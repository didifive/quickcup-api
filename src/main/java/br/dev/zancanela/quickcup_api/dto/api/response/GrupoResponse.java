package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Grupo;

public record GrupoResponse(
        Long id,
        String nome,
        String descricao
) {
    public static GrupoResponse fromEntity(Grupo entity) {
        return new GrupoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao()
        );
    }
}
