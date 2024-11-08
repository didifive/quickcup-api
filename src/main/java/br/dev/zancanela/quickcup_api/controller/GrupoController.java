package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.dto.admin.request.GrupoRequest;
import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.exception.QuickCupException;
import br.dev.zancanela.quickcup_api.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Controller
@RequestMapping("/grupo")
public class GrupoController {


    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping()
    public ModelAndView listaGrupos() {

        ModelAndView mv = new ModelAndView(VIEW_GRUPO_LISTA_HTML);

        List<Grupo> listaGrupos = grupoService.getAll();

        mv.addObject(MV_OBJECT_LISTA_GRUPOS, listaGrupos);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);

        return mv;

    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroGrupo(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(VIEW_GRUPO_FORM_HTML);

        Grupo grupo = new Grupo();
        if (id != null) {
            try {
                grupo = grupoService.getById(id);
            } catch (EntityNotFoundException e) {
                mv.addObject(MV_OBJECT_MENSAGEM_ERRO, e.getMessage());
            }
        }

        mv.addObject(MV_OBJECT_GRUPO_REQUEST, GrupoRequest.fromEntity(grupo));
        mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);

        return mv;

    }

    @PostMapping("/cadastro")
    public ModelAndView salvarGrupo(
            @Valid GrupoRequest grupoRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if(bindingResult.hasErrors()) {
            mv.setViewName(VIEW_GRUPO_FORM_HTML);
            mv.addObject(MV_OBJECT_GRUPO_REQUEST, grupoRequest);
            mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);
            return mv;
        }

        Grupo grupo;

        try{
            if (grupoRequest.id() == null) {
                grupo = grupoService.create(grupoRequest.toEntity());
            } else {
                grupo = grupoService.update(grupoRequest.id(), grupoRequest.toEntity());
            }
        } catch (QuickCupException e) {
            mv.setViewName(VIEW_GRUPO_LISTA_HTML);

            List<Grupo> listaGrupos = grupoService.getAll();

            mv.addObject(MV_OBJECT_MENSAGEM_ERRO, e.getMessage());
            mv.addObject(MV_OBJECT_LISTA_GRUPOS, listaGrupos);
            mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);
            return mv;
        }

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_GRUPO_REQUEST,
                grupoRequest);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_MENSAGEM_SUCESSO
                , "Grupo criado ou atualizado com sucesso.");

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE
                , CADASTROS);

        mv.setViewName(VIEW_REDIRECT_GRUPO + "/cadastro?id=" + grupo.getId());

        return mv;

    }

    @GetMapping("/excluir")
    public ModelAndView excluirGrupo(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView(VIEW_REDIRECT_GRUPO);

        try {
            grupoService.delete(id);
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_SUCESSO, "Grupo excluído com sucesso.");
        } catch (QuickCupException e) {
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_ERRO
                    , "Problema ao excluir: " + e.getMessage());
        }

        List<Grupo> listaGrupos = grupoService.getAll();
        mv.addObject(MV_OBJECT_LISTA_GRUPOS, listaGrupos);

        return mv;

    }
}
