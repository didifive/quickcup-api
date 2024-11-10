package br.dev.zancanela.quickcup_api.controller.api;

import br.dev.zancanela.quickcup_api.controller.api.docs.EmpresaRestControllerDocs;
import br.dev.zancanela.quickcup_api.dto.api.response.EmpresaResponse;
import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.service.EmpresaService;
import br.dev.zancanela.quickcup_api.service.FuncionamentoEspecialService;
import br.dev.zancanela.quickcup_api.service.FuncionamentoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaRestController implements EmpresaRestControllerDocs {

    private final EmpresaService empresaService;
    private final FuncionamentoService funcionamentoService;
    private final FuncionamentoEspecialService funcionamentoEspecialService;

    public EmpresaRestController(
            EmpresaService empresaService,
            FuncionamentoService funcionamentoService,
            FuncionamentoEspecialService funcionamentoEspecialService) {
        this.empresaService = empresaService;
        this.funcionamentoService = funcionamentoService;
        this.funcionamentoEspecialService = funcionamentoEspecialService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpresaResponse> getEmpresa() {
        Empresa empresa = empresaService.getEmpresa();
        boolean aberto = funcionamentoService.isOpen();
        List<Funcionamento> listaFuncionamentoSemana = funcionamentoService.getAll();
        listaFuncionamentoSemana.removeIf(
                funcionamento -> funcionamento.getHoraInicio() == null || funcionamento.getHoraFim() == null
        );
        List<FuncionamentoEspecial> listaFuncionamentoEspecial = funcionamentoEspecialService.getAll();

        return ResponseEntity.ok(
                EmpresaResponse.fromEntity(
                        empresa,
                        aberto,
                        listaFuncionamentoSemana,
                        listaFuncionamentoEspecial
                ));
    }
}
