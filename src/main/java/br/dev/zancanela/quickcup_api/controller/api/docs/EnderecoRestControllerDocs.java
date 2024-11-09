package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.EnderecoResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.EnderecoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

@Tag(name = "Endereço de entrega", description = "Operações relacionadas a endereço de entrega de clientes")
public interface EnderecoRestControllerDocs {

    @Operation(summary = "Adiciona um endereço ao cliente",
            description = "Adiciona um endereço ao cliente")
    @ApiResponse(responseCode = "200", description = "Endereço criado para o cliente")
    @ApiResponse(responseCode = "400", description = "Endereço com dados inválidos")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    ResponseEntity<EnderecoResponse> adicionaEndereco(
            EnderecoRequest enderecoRequest
            , BindingResult bindingResult);

    @Operation(summary = "Busca os endereços do cliente",
            description = "Busca os endereços do cliente")
    @Parameter(in = ParameterIn.PATH, name = "clienteId", description = "Id do cliente")
    @ApiResponse(responseCode = "200", description = "Lista de endereços do cliente")
    ResponseEntity<List<EnderecoResponse>> getEndereco(Long clienteId);

    @Operation(summary = "Atualiza um endereço",
            description = "Atualiza um endereço pelo id")
    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id do endereço")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado")
    @ApiResponse(responseCode = "400", description = "Endereço com dados inválidos")
    @ApiResponse(responseCode = "404", description = "Endereço ou cliente não encontrado")
    ResponseEntity<EnderecoResponse> atualizaEndereco(
            Long id,
            EnderecoRequest enderecoRequest,
            BindingResult bindingResult);

    @Operation(summary = "Exclui um endereço",
            description = "Exclui um endereço pelo id")
    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id do endereço")
    @ApiResponse(responseCode = "204", description = "Endereço excluido")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    ResponseEntity<Void> excluiEndereco(Long id);
}
