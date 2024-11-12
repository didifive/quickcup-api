package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String telefone
) {
    public static ClienteResponse fromEntity(Cliente entity) {
        return new ClienteResponse(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone()
        );
    }
}
