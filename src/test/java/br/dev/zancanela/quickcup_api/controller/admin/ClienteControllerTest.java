package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.service.ClienteService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testListaClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome Teste");
        List<Cliente> listaClientes = Collections.singletonList(cliente);

        when(clienteService.getAll()).thenReturn(listaClientes);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_CLIENTE_LISTA_HTML),
                        model().attributeExists(MV_OBJECT_LISTA_CLIENTES),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_LISTA_CLIENTES, listaClientes),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, CADASTROS)
                );
    }
}
