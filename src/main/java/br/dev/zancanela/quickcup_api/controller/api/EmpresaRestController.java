package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.dto.api.response.EmpresaResponse;
import br.dev.zancanela.quickcup_api.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaRestController {

    private final EmpresaService empresaService;

    public EmpresaRestController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping()
    public ResponseEntity<EmpresaResponse> getEmpresa() {
        return ResponseEntity.ok(EmpresaResponse.fromEntity(empresaService.getEmpresa()));
    }
}
