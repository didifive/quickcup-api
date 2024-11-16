package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.GrupoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.QUICKCUP_API_KEY_SECURITY_SCHEMES;

@SecurityRequirement(name = QUICKCUP_API_KEY_SECURITY_SCHEMES)
@Tag(name = "Grupo", description = "Operações relacionadas aos grupos de produtos")
public interface GrupoRestControllerDocs {

    @Operation(summary = "Listar Grupos",
            description = "Listar Grupos de produtos")
    @ApiResponse(responseCode = "200", description = "Retorna lista de produtos")
    ResponseEntity<List<GrupoResponse>> listarGrupos();


}
