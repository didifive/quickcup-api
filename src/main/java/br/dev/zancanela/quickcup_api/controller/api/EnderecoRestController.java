package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.EnderecoRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.EnderecoResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.EnderecoRequest;
import br.dev.zancanela.quickcup_api.entity.Endereco;
import br.dev.zancanela.quickcup_api.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.BindingError.checkBindingResultError;

@RestController
@RequestMapping("/api/v1/endereco")
public class EnderecoRestController implements EnderecoRestControllerDocs {

    private final EnderecoService enderecoService;

    public EnderecoRestController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EnderecoResponse> adicionaEndereco(
            @RequestBody @Valid EnderecoRequest enderecoRequest
            , BindingResult bindingResult) {

        checkBindingResultError(bindingResult);

        Endereco endereco = enderecoService.create(enderecoRequest.toEntity());

        return ResponseEntity.ok().body(EnderecoResponse.fromEntity(endereco));
    }

    @GetMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnderecoResponse>> getEndereco(@PathVariable Long clienteId) {
        List<Endereco> enderecos = enderecoService.getAllByClienteId(clienteId);

        List<EnderecoResponse> enderecosResponse = enderecos.stream().map(EnderecoResponse::fromEntity).toList();

        return ResponseEntity.ok().body(enderecosResponse);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EnderecoResponse> atualizaEndereco(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoRequest enderecoRequest
            , BindingResult bindingResult) {

        checkBindingResultError(bindingResult);

        Endereco endereco = enderecoService.update(id, enderecoRequest.toEntity());

        return ResponseEntity.ok().body(EnderecoResponse.fromEntity(endereco));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluiEndereco(@PathVariable Long id) {

        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
