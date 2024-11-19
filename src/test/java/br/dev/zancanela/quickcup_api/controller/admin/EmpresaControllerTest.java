package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.request.EmpresaRequest;
import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.service.EmpresaService;
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

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmpresaController.class)
@ExtendWith(MockitoExtension.class)
class EmpresaControllerTest {
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpresaService empresaService;

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testDetalheEmpresa() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setId((short) 1);
        empresa.setNome("Empresa Teste");
        when(empresaService.getEmpresa()).thenReturn(empresa);
        mockMvc.perform(MockMvcRequestBuilders.get("/empresa"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_EMPRESA_DETALHE_HTML),
                        model().attributeExists(MV_OBJECT_EMPRESA),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_EMPRESA, empresa),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, EMPRESA));
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testGetEmpresa() throws Exception {
        Empresa empresa = new Empresa();
        empresa.setId((short) 1);
        empresa.setNome("Empresa Teste");

        when(empresaService.getById((short) 1)).thenReturn(empresa);

        mockMvc.perform(MockMvcRequestBuilders.get("/empresa/cadastro").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_EMPRESA_FORM_HTML))
                .andExpect(model().attributeExists(MV_OBJECT_EMPRESA_REQUEST))
                .andExpect(model().attributeExists(MV_OBJECT_CURRENT_PAGE))
                .andExpect(model().attribute(MV_OBJECT_EMPRESA_REQUEST, EmpresaRequest.fromEntity(empresa)))
                .andExpect(model().attribute(MV_OBJECT_CURRENT_PAGE, EMPRESA));
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarEmpresa() throws Exception {
        EmpresaRequest empresaRequest = new EmpresaRequest(
                (short) 1,
                "Empresa Teste",
                "email@example.com",
                "123456789",
                new BigDecimal("10.00"),
                "00:30",
                "12345678",
                "Rua Teste",
                123,
                "Complemento Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste",
                new BigDecimal("123.456"),
                new BigDecimal("-123.456"));
        Empresa empresa = new Empresa();
        empresa.setId((short) 1);
        empresa.setNome("Empresa Teste");

        when(empresaService.update(any(Empresa.class))).thenReturn(empresa);

        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/cadastro")
                        .flashAttr(MV_OBJECT_EMPRESA_REQUEST, empresaRequest)
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_EMPRESA),
                        flash().attributeExists(MV_OBJECT_EMPRESA),
                        flash().attributeExists(MV_OBJECT_MENSAGEM_SUCESSO),
                        flash().attribute(MV_OBJECT_EMPRESA, empresa),
                        flash().attribute(MV_OBJECT_MENSAGEM_SUCESSO, "Empresa atualizada com sucesso.")
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testSalvarEmpresaComErros() throws Exception {
        EmpresaRequest empresaRequest = new EmpresaRequest(
                null,
                "",
                "",
                "",
                BigDecimal.valueOf(-1),
                "",
                "",
                "",
                -1,
                "",
                "",
                "",
                "",
                null,
                null);
        mockMvc.perform(MockMvcRequestBuilders.post("/empresa/cadastro")
                .flashAttr(MV_OBJECT_EMPRESA_REQUEST, empresaRequest)
                .with(csrf()))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_EMPRESA_FORM_HTML),
                        model().attributeExists(MV_OBJECT_EMPRESA_REQUEST),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_EMPRESA_REQUEST, empresaRequest),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, EMPRESA))
                .andExpect(model().attributeHasFieldErrors(MV_OBJECT_EMPRESA_REQUEST, "nome"))
                .andExpect(model().attributeHasFieldErrorCode(MV_OBJECT_EMPRESA_REQUEST, "nome", "NotEmpty"));
    }
}
