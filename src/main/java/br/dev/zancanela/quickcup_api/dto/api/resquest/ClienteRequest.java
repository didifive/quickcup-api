package br.dev.zancanela.quickcup_api.dto.api.resquest;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotEmpty(message = "O nome deve ser preenchido!")
        String nome,
        String email,
        @NotEmpty(message = "O telefone deve ser preenchido!")
        @Size(min = 10, max = 11, message = "O telefone deve ter 10 ou 11 dígitos")
        String telefone
) {
        public Cliente toEntity() {
                Cliente cliente = new Cliente();
                cliente.setNome(nome());
                cliente.setEmail(email());
                cliente.setTelefone(telefone());
                return cliente;
        }
}
