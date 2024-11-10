package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.GrupoRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.GrupoResponse;
import br.dev.zancanela.quickcup_api.service.GrupoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grupo")
public class GrupoRestController implements GrupoRestControllerDocs {

    private final GrupoService grupoService;

    public GrupoRestController(
            GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GrupoResponse>> listarGrupos() {
        return ResponseEntity.ok(
                grupoService.getAll().stream()
                        .map(GrupoResponse::fromEntity)
                        .toList()
        );
    }
}
