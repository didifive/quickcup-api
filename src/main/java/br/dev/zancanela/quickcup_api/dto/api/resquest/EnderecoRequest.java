package br.dev.zancanela.quickcup_api.dto.api.resquest;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.Endereco;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EnderecoRequest(
        @NotNull(message = "O id do cliente deve ser preenchido!")
        Long clienteId,
        String nome,
        @NotEmpty(message = "O cep deve ser preenchido!")
        @Size(min = 8, max = 10, message = "O cep deve ter entre 8 e 10 digitos")
        String cep,
        @NotEmpty(message = "O logradouro deve ser preenchido!")
        @Size(min = 3, max = 255, message = "O logradouro deve ter entre 3 e 255 caracteres")
        String logradouro,
        @NotNull(message = "O número deve ser preenchido!")
        @Min(value = 0, message = "O número deve ser igual ou maior que 0")
        @Max(value = Integer.MAX_VALUE, message = "O número deve ser igual ou menor que " + Integer.MAX_VALUE)
        Integer numero,
        String complemento,
        String bairro,
        @NotEmpty(message = "A cidade deve ser preenchida!")
        String cidade,
        @NotEmpty(message = "O estado deve ser preenchido!")
        String estado,
        @NotNull(message = "A longitude deve ser preenchida!")
        BigDecimal longitude,
        @NotNull(message = "A latitude deve ser preenchida!")
        BigDecimal latitude
) {
    public Endereco toEntity() {
        Endereco endereco = new Endereco();
        endereco.setCliente(new Cliente(clienteId()));
        endereco.setCep(cep());
        endereco.setNome(nome());
        endereco.setLogradouro(logradouro());
        endereco.setNumero(numero());
        endereco.setComplemento(complemento());
        endereco.setBairro(bairro());
        endereco.setCidade(cidade());
        endereco.setEstado(estado());
        endereco.setLongitude(longitude());
        endereco.setLatitude(latitude());
        return endereco;
    }
}
