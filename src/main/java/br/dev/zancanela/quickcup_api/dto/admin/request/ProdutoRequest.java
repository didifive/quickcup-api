package br.dev.zancanela.quickcup_api.dto.admin.request;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.Produto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequest(
        Long id,
        String codigo,
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        String descricao,
        String imagem,
        @Min(value = 0, message = "O valor deve ser igual ou maior que 0")
        BigDecimal valorOriginal,
        @Min(value = 0, message = "O desconto deve ser igual ou maior que 0")
        BigDecimal valorDesconto,
        boolean enabled,
        @NotNull(message = "O grupo deve ser preenchido!")
        Long idGrupo
) {

    public static ProdutoRequest fromEntity(Produto entity) {
        return new ProdutoRequest(
                entity.getId(),
                entity.getCodigo(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getImagem(),
                entity.getValorOriginal(),
                entity.getValorDesconto(),
                entity.isEnabled(),
                entity.getGrupo() == null ? Long.MAX_VALUE : entity.getGrupo().getId()
        );
    }

    public Produto toEntity() {
        Produto entity = new Produto();
        entity.setId(id());
        entity.setCodigo(codigo());
        entity.setNome(nome());
        entity.setDescricao(descricao());
        entity.setImagem(imagem());
        entity.setValorOriginal(valorOriginal());
        entity.setValorDesconto(valorDesconto());
        entity.setEnabled(enabled());
        entity.setGrupo(new Grupo(idGrupo()));
        return entity;
    }
}
