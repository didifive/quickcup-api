package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.service.FuncionamentoEspecialService;
import br.dev.zancanela.quickcup_api.service.FuncionamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Controller
@RequestMapping("/funcionamento")
public class FuncionamentoController {


    private final FuncionamentoService funcionamentoService;
    private final FuncionamentoEspecialService funcionamentoEspecialService;

    public FuncionamentoController(
            FuncionamentoService funcionamentoService,
            FuncionamentoEspecialService funcionamentoEspecialService) {
        this.funcionamentoService = funcionamentoService;
        this.funcionamentoEspecialService = funcionamentoEspecialService;
    }

    @GetMapping()
    public ModelAndView listaFuncionamento() {

        ModelAndView mv = new ModelAndView(VIEW_FUNCIONAMENTO_LISTA_HTML);

        List<Funcionamento> listaFuncionamentoSemana = funcionamentoService.getAll();
        List<FuncionamentoEspecial> listaFuncionamentoEspecial = funcionamentoEspecialService.getAll();

        mv.addObject(MV_OBJECT_FUNCIONAMENTO_SEMANA_LISTA, listaFuncionamentoSemana);
        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA, listaFuncionamentoEspecial);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);

        return mv;

    }

    @GetMapping("funcionamento/cadastro-funcionamento")
    public ModelAndView cadastroFuncionamentoEspecial(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML);

        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        if (id != null) {
            try {
                funcionamentoEspecial = funcionamentoEspecialService.getById(id);
            } catch (EntityNotFoundException e) {
                mv.addObject(MV_OBJECT_MENSAGEM_ERRO, e.getMessage());
            }
        }

        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL, funcionamentoEspecial);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);

        return mv;

    }

}
