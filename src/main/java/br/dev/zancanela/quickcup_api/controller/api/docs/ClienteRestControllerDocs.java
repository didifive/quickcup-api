package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.ClienteResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.ClienteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.QUICKCUP_API_KEY_SECURITY_SCHEMES;

@SecurityRequirement(name = QUICKCUP_API_KEY_SECURITY_SCHEMES)
@Tag(name = "Cliente", description = "Operações relacionadas aos clientes")
public interface ClienteRestControllerDocs {

    @Operation(summary = "Cria ou atualiza um cliente",
            description = "Cria ou atualiza um cliente com base no telefone")
    @ApiResponse(responseCode = "200", description = "Cliente criado ou atualizado")
    ResponseEntity<ClienteResponse> cliente(
            ClienteRequest clienteRequest
            , BindingResult bindingResult);

    @Operation(summary = "Busca um cliente pelo id",
            description = "Busca um cliente pelo id")
    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id do cliente")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    ResponseEntity<ClienteResponse> getCliente(@PathVariable Long id);
}
