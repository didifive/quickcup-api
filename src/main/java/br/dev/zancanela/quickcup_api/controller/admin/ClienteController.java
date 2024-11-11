package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {


    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ModelAndView listaClientes() {

        ModelAndView mv = new ModelAndView(VIEW_CLIENTE_LISTA_HTML);

        List<Cliente> listaGrupos = clienteService.getAll();

        mv.addObject(MV_OBJECT_LISTA_CLIENTES, listaGrupos);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);

        return mv;

    }

}
