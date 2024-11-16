package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.util.ApiConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.MV_OBJECT_CURRENT_PAGE;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${security.api.key}")
    private String securityApiKey;

    @Value("${quickcup.users.default.password}")
    private String defaultPassword;

    @Value("${quickcup.link.formulario.testes}")
    private String linkFormularioTestes;

    @Value("${quickcup.link.aplicacao.cliente}")
    private String linkAplicacaoCliente;

    @GetMapping()
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView(ApiConstants.VIEW_INDEX);

        Map<String, String> indexVariables = new HashMap<>();
        indexVariables.put("apiKey", securityApiKey);
        indexVariables.put("defaultPassword", defaultPassword);
        indexVariables.put("linkFormularioTestes", linkFormularioTestes);
        indexVariables.put("linkAplicacaoCliente", linkAplicacaoCliente);

        mv.addAllObjects(indexVariables);

        mv.addObject(MV_OBJECT_CURRENT_PAGE, "index");

        return mv;

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
