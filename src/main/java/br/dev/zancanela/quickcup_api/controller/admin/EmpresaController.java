package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.request.EmpresaRequest;
import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {


    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping()
    public ModelAndView detalheEmpresa() {

        ModelAndView mv = new ModelAndView(VIEW_EMPRESA_DETALHE_HTML);

        Empresa empresa = empresaService.getEmpresa();

        mv.addObject(MV_OBJECT_EMPRESA, empresa);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, EMPRESA);

        return mv;

    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroEmpresa(@RequestParam Short id) {

        ModelAndView mv = new ModelAndView(VIEW_EMPRESA_FORM_HTML);

        Empresa empresa = empresaService.getById(id);

        mv.addObject(MV_OBJECT_EMPRESA_REQUEST, EmpresaRequest.fromEntity(empresa));
        mv.addObject(MV_OBJECT_CURRENT_PAGE, EMPRESA);

        return mv;

    }

    @PostMapping("/cadastro")
    public ModelAndView salvarEmpresa(
            @Valid EmpresaRequest empresaRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if(bindingResult.hasErrors()) {
            mv.setViewName(VIEW_EMPRESA_FORM_HTML);
            mv.addObject(MV_OBJECT_EMPRESA_REQUEST, empresaRequest);
            mv.addObject(MV_OBJECT_CURRENT_PAGE, EMPRESA);
            return mv;
        }

        Empresa empresa;

        empresa = empresaService.update(empresaRequest.toEntity());

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_EMPRESA
                , empresa);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_MENSAGEM_SUCESSO
                , "Empresa atualizada com sucesso.");

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE
                , EMPRESA);

        mv.setViewName(VIEW_REDIRECT_EMPRESA);

        return mv;

    }

}
