package br.dev.zancanela.quickcup_api.dto.request;

import br.dev.zancanela.quickcup_api.entity.Empresa;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EmpresaRequest(
        Short id,
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        @NotEmpty(message = "O email deve ser preenchido!")
        String email,
        @NotEmpty(message = "O telefone deve ser preenchido!")
        String telefone,
        @NotEmpty(message = "O cep deve ser preenchido!")
        String cep,
        @NotEmpty(message = "O logradouro deve ser preenchido!")
        String logradouro,
        @NotNull(message = "O número deve ser preenchido!")
        Integer numero,
        String complemento,
        String bairro,
        @NotEmpty(message = "O cidade deve ser preenchido!")
        String cidade,
        @NotEmpty(message = "O estado deve ser preenchido!")
        String estado,
        @NotEmpty(message = "O longitude deve ser preenchido!")
        BigDecimal longitude,
        @NotEmpty(message = "O latitude deve ser preenchido!")
        BigDecimal latitude
) {

    public Empresa toEntity() {
        Empresa entity = new Empresa();
        entity.setId(id());
        entity.setNome(nome());
        entity.setEmail(email());
        entity.setTelefone(telefone());
        entity.setCep(cep());
        entity.setLogradouro(logradouro());
        entity.setNumero(numero());
        entity.setComplemento(complemento());
        entity.setBairro(bairro());
        entity.setCidade(cidade());
        entity.setEstado(estado());
        entity.setLongitude(longitude());
        entity.setLatitude(latitude());
        return entity;
    }

    public static EmpresaRequest fromEntity(Empresa entity) {
        return new EmpresaRequest(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTelefone(),
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
