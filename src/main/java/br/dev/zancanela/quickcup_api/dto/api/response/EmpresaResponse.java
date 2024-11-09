package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Empresa;

import java.math.BigDecimal;

import static br.dev.zancanela.quickcup_api.util.DateTimeUtils.timeToStringTime;

public record EmpresaResponse(
        String nome,
        String email,
        String telefone,
        BigDecimal valorEntrega,
        String tempoEntrega,
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
    public static EmpresaResponse fromEntity(Empresa entity) {
        return new EmpresaResponse(
                entity.getNome(),
                entity.getEmail(),
                entity.getTelefone(),
                entity.getValorEntrega(),
                timeToStringTime(entity.getTempoEntrega()),
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
