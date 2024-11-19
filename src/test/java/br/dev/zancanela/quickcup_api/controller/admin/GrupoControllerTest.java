package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.request.GrupoRequest;
import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.service.GrupoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GrupoController.class)
@ExtendWith(MockitoExtension.class)
class GrupoControllerTest {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GrupoService grupoService;

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testListaGrupos() throws Exception {
        List<Grupo> listaGrupos = Collections.singletonList(new Grupo());
        when(grupoService.getAll()).thenReturn(listaGrupos);
        mockMvc.perform(MockMvcRequestBuilders.get("/grupo"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_GRUPO_LISTA_HTML),
                        model().attributeExists(MV_OBJECT_LISTA_GRUPOS),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_LISTA_GRUPOS, listaGrupos),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testCadastroGrupo() throws Exception {
        Grupo grupo = new Grupo();
        grupo.setId(1L);

        when(grupoService.getById(1L)).thenReturn(grupo);

        mockMvc.perform(MockMvcRequestBuilders.get("/grupo/cadastro")
                        .param("id", "1"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_GRUPO_FORM_HTML),
                        model().attributeExists(MV_OBJECT_GRUPO_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_GRUPO_REQUEST, GrupoRequest.fromEntity(grupo)),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarGrupo() throws Exception {
        GrupoRequest grupoRequest = new GrupoRequest(
                1L, "Grupo Teste", "Descrição Teste");

        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setNome("Grupo Teste");

        when(grupoService.update(any(Long.class), any(Grupo.class))).thenReturn(grupo);

        mockMvc.perform(MockMvcRequestBuilders.post("/grupo/cadastro")
                        .flashAttr(MV_OBJECT_GRUPO_REQUEST, grupoRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_GRUPO + "/cadastro?id=" + grupo.getId()),
                        flash().attributeExists(MV_OBJECT_GRUPO_REQUEST),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_GRUPO_REQUEST, GrupoRequest.fromEntity(grupo)),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Grupo criado ou atualizado com sucesso.")
                );
    }

    @Test
    @WithMockUser(username =ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarGrupoComErros() throws Exception {
        GrupoRequest grupoRequest = new GrupoRequest(
                null, "", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/grupo/cadastro")
                        .flashAttr(MV_OBJECT_GRUPO_REQUEST, grupoRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_GRUPO_FORM_HTML),
                        model().attributeExists(MV_OBJECT_GRUPO_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_GRUPO_REQUEST, grupoRequest),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS),
                        model().attributeHasFieldErrors(MV_OBJECT_GRUPO_REQUEST, "nome")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testExcluirGrupo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/grupo/excluir")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name(VIEW_REDIRECT_GRUPO))
                .andExpect(flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO))
                .andExpect(flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Grupo excluído com sucesso."));
    }

}
