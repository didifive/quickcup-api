package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Produto", description = "Operações relacionadas aos produtos")
public interface ProdutoRestControllerDocs {

    @Operation(summary = "Listar Produtos Ativos",
            description = "Listar Produtos que estão ativos")
    @ApiResponse(responseCode = "200", description = "Retorna lista de produtos ativos")
    ResponseEntity<List<ProdutoResponse>> listarProdutosAtivos();
}
