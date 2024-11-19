package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.config.ApiKeyAuthorizationManager;
import br.dev.zancanela.quickcup_api.config.PassEncoder;
import br.dev.zancanela.quickcup_api.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.MV_OBJECT_CURRENT_PAGE;
import static br.dev.zancanela.quickcup_api.util.ApiConstants.VIEW_INDEX;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IndexController.class)
@ExtendWith(MockitoExtension.class)
@Import({ApiKeyAuthorizationManager.class, PassEncoder.class,SecurityConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_INDEX),
                        model().attributeExists("apiKey"),
                        model().attributeExists("defaultPassword"),
                        model().attributeExists("linkFormularioTestes"),
                        model().attributeExists("linkAplicacaoCliente"),
                        model().attributeExists("currentPage"),
                        model().attribute("apiKey", "apiKeyTest"),
                        model().attribute("defaultPassword", "passwordTest"),
                        model().attribute("linkFormularioTestes", "https://test.form"),
                        model().attribute("linkAplicacaoCliente", "https://client.app"),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, "index")
                );
    }

    @Test
    @WithAnonymousUser
    void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpectAll(
                        status().isOk(),
                        view().name("login")
                );
    }
}