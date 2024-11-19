package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.request.FuncionamentoEspecialRequest;
import br.dev.zancanela.quickcup_api.dto.admin.request.FuncionamentoSemanalRequest;
import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo;
import br.dev.zancanela.quickcup_api.service.FuncionamentoEspecialService;
import br.dev.zancanela.quickcup_api.service.FuncionamentoService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FuncionamentoController.class)
@ExtendWith(MockitoExtension.class)
class FuncionamentoControllerTest {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FuncionamentoService funcionamentoService;
    @MockBean
    private FuncionamentoEspecialService funcionamentoEspecialService;

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testListaFuncionamento() throws Exception {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setTipo(FuncionamentoEspecialTipo.ABERTO);

        List<Funcionamento> listaFuncionamentoSemana = Collections.singletonList(new Funcionamento());
        List<FuncionamentoEspecial> listaFuncionamentoEspecial = Collections.singletonList(funcionamentoEspecial);
        when(funcionamentoService.getAll()).thenReturn(listaFuncionamentoSemana);
        when(funcionamentoEspecialService.getAll()).thenReturn(listaFuncionamentoEspecial);
        mockMvc.perform(MockMvcRequestBuilders.get("/funcionamento"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_FUNCIONAMENTO_LISTA_HTML),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_SEMANAL_LISTA),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_SEMANAL_LISTA, listaFuncionamentoSemana),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA, listaFuncionamentoEspecial),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testCadastroFuncionamentoEspecial() throws Exception {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setId(1L);

        when(funcionamentoEspecialService.getById(1L)).thenReturn(funcionamentoEspecial);

        mockMvc.perform(MockMvcRequestBuilders.get("/funcionamento/cadastro-especial").param("id", "1"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, FuncionamentoEspecialRequest.fromEntity(funcionamentoEspecial)),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA, List.of(FuncionamentoEspecialTipo.ABERTO, FuncionamentoEspecialTipo.FECHADO)),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarFuncionamentoEspecial() throws Exception {
        FuncionamentoEspecialRequest funcionamentoEspecialRequest = new FuncionamentoEspecialRequest(
                1L,
                "Teste",
                FuncionamentoEspecialTipo.ABERTO,
                "2024-01-01T12:00",
                "2024-01-01T15:00");

        when(funcionamentoEspecialService.create(any(FuncionamentoEspecial.class))).thenReturn(new FuncionamentoEspecial());

        mockMvc.perform(MockMvcRequestBuilders.post("/funcionamento/cadastro-especial")
                        .flashAttr(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, funcionamentoEspecialRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_FUNCIONAMENTO),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Funcionamento especial atualizado com sucesso.")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarFuncionamentoEspecialComErros() throws Exception {
        FuncionamentoEspecialRequest funcionamentoEspecialRequest = new FuncionamentoEspecialRequest(
                1L, "", null, null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/funcionamento/cadastro-especial")
                        .flashAttr(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, funcionamentoEspecialRequest)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML))
                .andExpect(model().attributeExists(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST))
                .andExpect(model().attributeExists(MV_OBJECT_CURRENT_PAGE))
                .andExpect(model().attribute(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, funcionamentoEspecialRequest))
                .andExpect(model().attribute(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO))
                .andExpect(model().attributeHasFieldErrors(MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST, "nome", "tipo", "dataInicio", "dataFim"));
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testExcluirFuncionamentoEspecial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/funcionamento/excluir-especial").param("id", "1").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(view().name(VIEW_REDIRECT_FUNCIONAMENTO))
                .andExpect(flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO))
                .andExpect(flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Funcionamento especial excluído com sucesso."));
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testCadastroFuncionamentoSemanal() throws Exception {
        Funcionamento funcionamento = new Funcionamento();
        funcionamento.setDiaSemana(DiaSemana.SEGUNDA);
        when(funcionamentoService.getById(DiaSemana.SEGUNDA)).thenReturn(funcionamento);
        mockMvc.perform(MockMvcRequestBuilders.get("/funcionamento/cadastro-semanal")
                        .param("id", "SEGUNDA"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_FUNCIONAMENTO_FORM_SEMANAL_HTML),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST, FuncionamentoSemanalRequest.fromEntity(funcionamento)),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarFuncionamentoSemanal() throws Exception {
        FuncionamentoSemanalRequest funcionamentoSemanalRequest =
                new FuncionamentoSemanalRequest(DiaSemana.SEGUNDA, "08:00", "18:00");
        Funcionamento funcionamento = new Funcionamento();
        funcionamento.setDiaSemana(DiaSemana.SEGUNDA);
        when(funcionamentoService.create(any(Funcionamento.class))).thenReturn(funcionamento);
        mockMvc.perform(MockMvcRequestBuilders.post("/funcionamento/cadastro-semanal")
                .flashAttr(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST, funcionamentoSemanalRequest)
                .with(csrf()))
                .andExpectAll(status().isFound(),
                        view().name(VIEW_REDIRECT_FUNCIONAMENTO),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Funcionamento semanal [SEGUNDA] atualizado com sucesso."));
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarFuncionamentoSemanalComErros() throws Exception {
        FuncionamentoSemanalRequest funcionamentoSemanalRequest =
                new FuncionamentoSemanalRequest(null, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/funcionamento/cadastro-semanal")
                .flashAttr(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST, funcionamentoSemanalRequest)
                .with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_FUNCIONAMENTO_FORM_SEMANAL_HTML),
                        model().attributeExists(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST, funcionamentoSemanalRequest),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, FUNCIONAMENTO),
                        model().attributeHasFieldErrors(MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST, "diaSemana", "horaAbertura", "horaFechamento")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testExcluirFuncionamentoSemanal() throws Exception {
        doNothing().when(funcionamentoService).delete(DiaSemana.SEGUNDA);

        mockMvc.perform(MockMvcRequestBuilders.get("/funcionamento/excluir-semanal")
                        .param("id", "SEGUNDA")
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_FUNCIONAMENTO),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Funcionamento semanal [SEGUNDA] excluído com sucesso.")
                );
    }

}
