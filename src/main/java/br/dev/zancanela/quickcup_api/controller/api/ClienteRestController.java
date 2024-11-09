package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.ClienteRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.ClienteResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.ClienteRequest;
import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static br.dev.zancanela.quickcup_api.util.BindingError.checkBindingResultError;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteRestController implements ClienteRestControllerDocs {

    private final ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteResponse> cliente(
            @RequestBody @Valid ClienteRequest clienteRequest
            , BindingResult bindingResult) {

        checkBindingResultError(bindingResult);

        Cliente cliente = clienteService.create(clienteRequest.toEntity());

        return ResponseEntity.ok().body(ClienteResponse.fromEntity(cliente));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteResponse> getCliente(@PathVariable Long id) {
        return ResponseEntity.ok(
                ClienteResponse.fromEntity(clienteService.getById(id))
        );
    }
}
