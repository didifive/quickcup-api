package br.dev.zancanela.quickcup_api.dto.api.response;

import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String complemento,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String bairro,
        String cidade,
        String estado,
        BigDecimal longitude,
        BigDecimal latitude,
        boolean aberto,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<FuncionamentoSemanalResponse> funcionamentoSemanal,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<FuncionamentoEspecialResponse> funcionamentosEspeciais
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
                entity.getLatitude(),
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static EmpresaResponse fromEntity(
            Empresa entity,
            boolean aberto,
            List<Funcionamento> funcionamentoSemanal,
            List<FuncionamentoEspecial> funcionamentosEspeciais) {

        List<FuncionamentoSemanalResponse> funcionamentoSemanalResponse = new ArrayList<>();
        if (funcionamentoSemanal != null) {
            funcionamentoSemanalResponse =
                    funcionamentoSemanal.stream().map(FuncionamentoSemanalResponse::fromEntity).toList();
        }

        List<FuncionamentoEspecialResponse> funcionamentosEspeciaisResponse = new ArrayList<>();
        if (funcionamentosEspeciais != null) {
            funcionamentosEspeciaisResponse =
                    funcionamentosEspeciais.stream().map(FuncionamentoEspecialResponse::fromEntity).toList();
        }

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
                entity.getLatitude(),
                aberto,
                funcionamentoSemanalResponse,
                funcionamentosEspeciaisResponse
        );
    }
}
