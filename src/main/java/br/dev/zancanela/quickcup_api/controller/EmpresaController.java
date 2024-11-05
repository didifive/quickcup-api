package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.dto.request.EmpresaRequest;
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

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping()
    public ModelAndView detalheEmpresa() {

        ModelAndView mv = new ModelAndView("empresa/detalhe.html");

        dadosEmpresa(mv);

        return mv;

    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroEmpresa(@RequestParam Short id) {

        ModelAndView mv = new ModelAndView("empresa/form.html");

        Empresa empresa = empresaService.getById(id);

        mv.addObject("empresaRequest", EmpresaRequest.fromEntity(empresa));

        return mv;

    }

    @PostMapping("/cadastro")
    public ModelAndView salvarUnidadeMedida(
            @Valid EmpresaRequest empresaRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if(bindingResult.hasErrors()) {
            mv.setViewName("empresa/form.html");
            mv.addObject("empresaRequest", empresaRequest);
            return mv;
        }

        Empresa empresa;

        empresa = empresaService.update(empresaRequest.toEntity());

        redirectAttributes.addFlashAttribute(
                "empresa"
                , empresa);

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso"
                , "Empresa atualizada com sucesso.");

        mv.setViewName("redirect:/empresa");

        return mv;

    }

    private void dadosEmpresa(ModelAndView mv) {
        Empresa empresa = empresaService.getEmpresa();

        mv.addObject("empresa", empresa);
    }

}
