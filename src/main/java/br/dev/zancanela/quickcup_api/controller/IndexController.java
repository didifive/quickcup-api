package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.util.ApiConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.MV_OBJECT_CURRENT_PAGE;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping()
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView(ApiConstants.VIEW_INDEX);

        mv.addObject(MV_OBJECT_CURRENT_PAGE, "index");

        return mv;

    }

}
