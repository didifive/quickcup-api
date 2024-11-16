package br.dev.zancanela.quickcup_api.controller.api.docs;

import br.dev.zancanela.quickcup_api.dto.api.response.EmpresaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.QUICKCUP_API_KEY_SECURITY_SCHEMES;

@SecurityRequirement(name = QUICKCUP_API_KEY_SECURITY_SCHEMES)
@Tag(name = "Empresa", description = "Operações relacionadas a empresa")
public interface EmpresaRestControllerDocs {

    @Operation(summary = "Busca a empresa",
            description = "Busca a empresa cadastrada no portal administrativo")
    @ApiResponse(responseCode = "200", description = "Retorna empresa")
    ResponseEntity<EmpresaResponse> getEmpresa();
}
