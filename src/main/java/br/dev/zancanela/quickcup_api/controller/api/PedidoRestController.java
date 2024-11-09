package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.PedidoRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.PedidoResponse;
import br.dev.zancanela.quickcup_api.dto.api.resquest.PedidoRequest;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.dev.zancanela.quickcup_api.util.BindingError.checkBindingResultError;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoRestController implements PedidoRestControllerDocs {

    private final PedidoService pedidoService;

    public PedidoRestController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponse> novoPedido(
            @RequestBody @Valid PedidoRequest pedidoRequest
            , BindingResult bindingResult) {

        checkBindingResultError(bindingResult);

        Pedido pedido = pedidoService.create(pedidoRequest.toEntity());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedido.getId().toString()).toUri();

        return ResponseEntity.created(uri).body(PedidoResponse.fromEntity(pedido));
    }

    @GetMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PedidoResponse>> getPedidos(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.getAllByClienteId(clienteId);

        return ResponseEntity.ok(
                pedidos.stream().map(PedidoResponse::fromEntity).toList()
        );
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PedidoResponse> getPedido(@PathVariable Long id) {
        return ResponseEntity.ok(
                PedidoResponse.fromEntity(pedidoService.getById(id))
        );
    }
}
