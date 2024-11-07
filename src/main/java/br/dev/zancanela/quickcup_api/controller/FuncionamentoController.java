package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.dto.admin.request.FuncionamentoEspecialRequest;
import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.service.FuncionamentoEspecialService;
import br.dev.zancanela.quickcup_api.service.FuncionamentoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @GetMapping("/cadastro-especial")
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

        List<FuncionamentoEspecialTipo> tiposFuncionamento = new ArrayList<>();
        tiposFuncionamento.add(FuncionamentoEspecialTipo.ABERTO);
        tiposFuncionamento.add(FuncionamentoEspecialTipo.FECHADO);

        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, FuncionamentoEspecialRequest.fromEntity(funcionamentoEspecial));
        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA, tiposFuncionamento);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);

        return mv;
    }

    @PostMapping("/cadastro-especial")
    public ModelAndView salvarFuncionamentoEspecial(
            @Valid FuncionamentoEspecialRequest funcionamentoEspecialRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mv.setViewName(VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML);
            mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, funcionamentoEspecialRequest);
            mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);
            return mv;
        }

        try {
            funcionamentoEspecialService.create(funcionamentoEspecialRequest.toEntity());
            String tipo = funcionamentoEspecialRequest.id() == null ? "cadastrado" : "atualizado";
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_SUCESSO
                    , "Funcionamento especial " +
                            tipo +
                            " com sucesso.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_ERRO,
                    e.getMessage());
        }

        List<Funcionamento> listaFuncionamentoSemana = funcionamentoService.getAll();
        List<FuncionamentoEspecial> listaFuncionamentoEspecial = funcionamentoEspecialService.getAll();
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_FUNCIONAMENTO_SEMANA_LISTA, listaFuncionamentoSemana);
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA, listaFuncionamentoEspecial);
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);

        mv.setViewName(VIEW_REDIRECT_FUNCIONAMENTO);

        return mv;

    }

    @GetMapping("/excluir-especial")
    public ModelAndView excluirFuncionamentoEspecial(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes
    ) {

        ModelAndView mv = new ModelAndView(VIEW_REDIRECT_FUNCIONAMENTO);

        try {
            funcionamentoEspecialService.delete(id);
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_SUCESSO, "Funcionamento especial excluído com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_ERRO
                    , "Problema ao excluir: " + e.getMessage());
        }

        List<Funcionamento> listaFuncionamentoSemana = funcionamentoService.getAll();
        List<FuncionamentoEspecial> listaFuncionamentoEspecial = funcionamentoEspecialService.getAll();
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_FUNCIONAMENTO_SEMANA_LISTA, listaFuncionamentoSemana);
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA, listaFuncionamentoEspecial);
        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);

        return mv;
    }

//    @GetMapping("/cadastro-semanal")
//    public ModelAndView cadastroFuncionamentoSemanal() {
//
//        ModelAndView mv = new ModelAndView(VIEW_FUNCIONAMENTO_FORM_SEMANAL_HTML);
//
//        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
//        if (id != null) {
//            try {
//                funcionamentoEspecial = funcionamentoEspecialService.getById(id);
//            } catch (EntityNotFoundException e) {
//                mv.addObject(MV_OBJECT_MENSAGEM_ERRO, e.getMessage());
//            }
//        }
//
//        List<FuncionamentoEspecialTipo> tiposFuncionamento = new ArrayList<>();
//        tiposFuncionamento.add(FuncionamentoEspecialTipo.ABERTO);
//        tiposFuncionamento.add(FuncionamentoEspecialTipo.FECHADO);
//
//        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, FuncionamentoEspecialRequest.fromEntity(funcionamentoEspecial));
//        mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA, tiposFuncionamento);
//        mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);
//
//        return mv;
//    }
//
//    @PostMapping("/cadastro-semanal")
//    public ModelAndView salvarFuncionamentoSemanal(
//            @Valid FuncionamentoEspecialRequest funcionamentoEspecialRequest
//            , BindingResult bindingResult
//            , RedirectAttributes redirectAttributes) {
//
//        ModelAndView mv = new ModelAndView();
//
//        if (bindingResult.hasErrors()) {
//            mv.setViewName(VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML);
//            mv.addObject(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, funcionamentoEspecialRequest);
//            mv.addObject(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);
//            return mv;
//        }
//
//        try {
//            funcionamentoEspecialService.create(funcionamentoEspecialRequest.toEntity());
//            String tipo = funcionamentoEspecialRequest.id() == null ? "cadastrado" : "atualizado";
//            redirectAttributes.addFlashAttribute(
//                    MV_OBJECT_MENSAGEM_SUCESSO
//                    , "Funcionamento especial " +
//                            tipo +
//                            " com sucesso.");
//        } catch (DataIntegrityViolationException e) {
//            redirectAttributes.addFlashAttribute(
//                    MV_OBJECT_MENSAGEM_ERRO,
//                    e.getMessage());
//        }
//
//        List<Funcionamento> listaFuncionamentoSemana = funcionamentoService.getAll();
//        List<FuncionamentoEspecial> listaFuncionamentoEspecial = funcionamentoEspecialService.getAll();
//        redirectAttributes.addFlashAttribute(
//                MV_OBJECT_FUNCIONAMENTO_SEMANA_LISTA, listaFuncionamentoSemana);
//        redirectAttributes.addFlashAttribute(
//                MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA, listaFuncionamentoEspecial);
//        redirectAttributes.addFlashAttribute(
//                MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO);
//
//        mv.setViewName(VIEW_REDIRECT_FUNCIONAMENTO);
//
//        return mv;
//
//    }

}
