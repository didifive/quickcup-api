package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Endereco;

import java.math.BigDecimal;

public record EnderecoResponse(
        Long id,
        String nome,
        String cep,
        String logradouro,
        Integer numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        BigDecimal longitude,
        BigDecimal latitude
) {
    public static EnderecoResponse fromEntity(Endereco entity) {
        return new EnderecoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCep(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getLongitude(),
                entity.getLatitude()
        );
    }
}
