package br.dev.zancanela.quickcup_api.controller;

import br.dev.zancanela.quickcup_api.dto.admin.request.ProdutoRequest;
import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.exception.QuickCupException;
import br.dev.zancanela.quickcup_api.service.GrupoService;
import br.dev.zancanela.quickcup_api.service.ProdutoService;
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
@RequestMapping("/produto")
public class ProdutoController {


    private final ProdutoService produtoService;
    private final GrupoService grupoService;

    public ProdutoController(
            ProdutoService produtoService,
            GrupoService grupoService) {
        this.produtoService = produtoService;
        this.grupoService = grupoService;
    }

    @GetMapping()
    public ModelAndView listaProdutos() {

        return getListaProdutos();

    }

    private ModelAndView getListaProdutos() {
        ModelAndView mv = new ModelAndView(VIEW_PRODUTO_LISTA_HTML);

        List<Produto> listaProdutos = produtoService.getAll();

        mv.addObject(MV_OBJECT_LISTA_PRODUTOS, listaProdutos);

        mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);

        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroProduto(@RequestParam(required = false) Long id) {

        ModelAndView mv = new ModelAndView(VIEW_PRODUTO_FORM_HTML);

        Produto produto = new Produto();
        if (id != null) {
            try {
                produto = produtoService.getById(id);
            } catch (EntityNotFoundException e) {
                mv.addObject(MV_OBJECT_MENSAGEM_ERRO, e.getMessage());
            }
        }

        List<Grupo> listaGrupos = grupoService.getAll();

        mv.addObject(MV_OBJECT_PRODUTO_REQUEST, ProdutoRequest.fromEntity(produto));
        mv.addObject(MV_OBJECT_LISTA_GRUPOS, listaGrupos);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);

        return mv;

    }

    @PostMapping("/cadastro")
    public ModelAndView salvarProduto(
            @Valid ProdutoRequest produtoRequest
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mv.setViewName(VIEW_PRODUTO_FORM_HTML);
            mv.addObject(MV_OBJECT_PRODUTO_REQUEST, produtoRequest);
            mv.addObject(MV_OBJECT_CURRENT_PAGE, CADASTROS);
            return mv;
        }

        Produto produto;

        try {
            if (produtoRequest.id() == null) {
                produto = produtoService.create(produtoRequest.toEntity());
            } else {
                produto = produtoService.update(produtoRequest.id(), produtoRequest.toEntity());
            }
        } catch (QuickCupException e) {
            return getListaProdutos();
        }

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_PRODUTO_REQUEST,
                ProdutoRequest.fromEntity(produto));

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_MENSAGEM_SUCESSO
                , "Produto criado ou atualizado com sucesso.");

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE
                , CADASTROS);

        mv.setViewName(VIEW_REDIRECT_PRODUTO + "/cadastro?id=" + produto.getId());

        return mv;

    }

    @GetMapping("/excluir")
    public ModelAndView excluirProduto(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView(VIEW_REDIRECT_PRODUTO);

        try {
            produtoService.delete(id);
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_SUCESSO, "Produto excluído com sucesso.");
        } catch (QuickCupException e) {
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_ERRO
                    , "Problema ao excluir produto: " + e.getMessage());
        }

        List<Produto> listaProdutos = produtoService.getAll();
        mv.addObject(MV_OBJECT_LISTA_PRODUTOS, listaProdutos);

        return mv;

    }
}
