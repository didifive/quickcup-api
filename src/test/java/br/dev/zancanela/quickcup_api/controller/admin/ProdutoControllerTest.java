package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.request.ProdutoRequest;
import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.service.GrupoService;
import br.dev.zancanela.quickcup_api.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProdutoController.class)
@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProdutoService produtoService;
    @MockBean
    private GrupoService grupoService;

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testListaProdutos() throws Exception {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setNome("Grupo Teste");

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setGrupo(grupo);

        List<Produto> listaProdutos = Collections.singletonList(produto);

        when(produtoService.getAll()).thenReturn(listaProdutos);

        mockMvc.perform(MockMvcRequestBuilders.get("/produto"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_PRODUTO_LISTA_HTML),
                        model().attributeExists(MV_OBJECT_LISTA_PRODUTOS),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_LISTA_PRODUTOS, listaProdutos),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testCadastroProduto() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoService.getById(1L)).thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.get("/produto/cadastro")
                        .param("id", "1"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_PRODUTO_FORM_HTML),
                        model().attributeExists(MV_OBJECT_PRODUTO_REQUEST),
                        model().attributeExists(MV_OBJECT_LISTA_GRUPOS),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_PRODUTO_REQUEST, ProdutoRequest.fromEntity(produto)),
                        model().attribute(MV_OBJECT_LISTA_GRUPOS, Collections.emptyList()),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarProduto() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest(
                1L,
                "P1",
                "Produto Teste",
                "Descrição Teste",
                "",
                BigDecimal.TEN,
                BigDecimal.ZERO,
                true,
                1L);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoService.update(any(Long.class), any(Produto.class))).thenReturn(produto);

        mockMvc.perform(MockMvcRequestBuilders.post("/produto/cadastro")
                        .flashAttr("produtoRequest", produtoRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_PRODUTO + "/cadastro?id=" + produto.getId()),
                        flash().attributeExists(MV_OBJECT_PRODUTO_REQUEST),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_PRODUTO_REQUEST, ProdutoRequest.fromEntity(produto)),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Produto criado ou atualizado com sucesso.")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarProdutoComErros() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest(
                null, "", "", "", "", null, null, true, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/produto/cadastro")
                        .flashAttr("produtoRequest", produtoRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_PRODUTO_FORM_HTML),
                        model().attributeExists(MV_OBJECT_PRODUTO_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_PRODUTO_REQUEST, produtoRequest),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS),
                        model().attributeHasFieldErrors(MV_OBJECT_PRODUTO_REQUEST,
                                "nome","idGrupo")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testExcluirProduto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/excluir")
                        .param("id", "1").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name(VIEW_REDIRECT_PRODUTO))
                .andExpect(flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO))
                .andExpect(flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Produto excluído com sucesso."));
    }



}