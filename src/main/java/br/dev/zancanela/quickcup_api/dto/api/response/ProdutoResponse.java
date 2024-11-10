package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long grupoId,
        Long id,
        String codigo,
        String nome,
        String descricao,
        String imagem,
        BigDecimal valorOriginal,
        BigDecimal valorDesconto
) {
    public static ProdutoResponse fromEntity(Produto entity) {
        return new ProdutoResponse(
                entity.getGrupo().getId(),
                entity.getId(),
                entity.getCodigo(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getImagem(),
                entity.getValorOriginal(),
                entity.getValorDesconto()
        );
    }
}
