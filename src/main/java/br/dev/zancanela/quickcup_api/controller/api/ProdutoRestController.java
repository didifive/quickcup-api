package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.ProdutoRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.ProdutoResponse;
import br.dev.zancanela.quickcup_api.service.ProdutoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoRestController implements ProdutoRestControllerDocs {

    private final ProdutoService produtoService;

    public ProdutoRestController(
            ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoResponse>> listarProdutosAtivos() {
        return ResponseEntity.ok(
                produtoService.getAllEnabled().stream()
                        .map(ProdutoResponse::fromEntity)
                        .toList()
        );
    }
}
