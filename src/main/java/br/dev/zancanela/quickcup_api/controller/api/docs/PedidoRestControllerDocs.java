package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.PedidoResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.PedidoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.QUICKCUP_API_KEY_SECURITY_SCHEMES;

@SecurityRequirement(name = QUICKCUP_API_KEY_SECURITY_SCHEMES)
@Tag(name = "Pedido", description = "Operações relacionadas aos pedidos")
public interface PedidoRestControllerDocs {

    @Operation(summary = "Busca a empresa",
            description = "Busca a empresa cadastrada no portal administrativo")
    @ApiResponse(responseCode = "201", description = "Retorna novo pedido criado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado")
    @ApiResponse(responseCode = "409", description = "Problema com validação do pedido")
    ResponseEntity<PedidoResponse> novoPedido(
            PedidoRequest pedidoRequest,
            BindingResult bindingResult);

    @Operation(summary = "Busca os pedidos do cliente",
            description = "Busca os pedidos do cliente")
    @Parameter(in = ParameterIn.PATH, name = "clienteId", description = "Id do cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos do cliente")
    @ApiResponse(responseCode = "404", description = "Cliente informado não encontrado")
    ResponseEntity<List<PedidoResponse>> getPedidos(Long clienteId);

    @Operation(summary = "Busca um pedido",
            description = "Busca um pedido pelo id")
    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id do pedido")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido informado não encontrado")
    ResponseEntity<PedidoResponse> getPedido(Long id);
}
