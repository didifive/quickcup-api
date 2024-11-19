package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.response.PedidoResponse;
import br.dev.zancanela.quickcup_api.entity.Cliente;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;
import br.dev.zancanela.quickcup_api.service.PedidoService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PedidoController.class)
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PedidoService pedidoService;


    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testListaPedidos() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setTelefone("11999998888");

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setValorOriginal(BigDecimal.TEN);
        produto.setValorDesconto(BigDecimal.ZERO);
        produto.setEnabled(true);

        ItemPedido item = new ItemPedido();
        item.setId(new ItemPedidoId(new Pedido(), produto));
        item.setQuantidade(1);
        item.setValorUnitarioDesconto(BigDecimal.ZERO);
        item.setValorUnitarioOriginal(BigDecimal.TEN);

        Pedido pedidoNovo = new Pedido();
        pedidoNovo.setId(1L);
        pedidoNovo.setCliente(cliente);
        pedidoNovo.setItens(new ArrayList<>());
        pedidoNovo.setValorEntrega(BigDecimal.ZERO);
        pedidoNovo.setStatus(PedidoStatus.NOVO);
        pedidoNovo.setItens(Collections.singletonList(item));

        Pedido pedidoConfirmado = new Pedido();
        pedidoConfirmado.setId(2L);
        pedidoConfirmado.setCliente(cliente);
        pedidoConfirmado.setItens(new ArrayList<>());
        pedidoConfirmado.setValorEntrega(BigDecimal.ZERO);
        pedidoConfirmado.setStatus(PedidoStatus.CONFIRMADO);
        pedidoConfirmado.setItens(Collections.singletonList(item));

        Pedido pedidoEmPreparo = new Pedido();
        pedidoEmPreparo.setItens(new ArrayList<>());
        pedidoEmPreparo.setId(3L);
        pedidoEmPreparo.setCliente(cliente);
        pedidoEmPreparo.setValorEntrega(BigDecimal.ZERO);
        pedidoEmPreparo.setStatus(PedidoStatus.EM_PREPARO);
        pedidoEmPreparo.setItens(Collections.singletonList(item));

        Pedido pedidoEmEntrega = new Pedido();
        pedidoEmEntrega.setId(4L);
        pedidoEmEntrega.setCliente(cliente);
        pedidoEmEntrega.setItens(new ArrayList<>());
        pedidoEmEntrega.setValorEntrega(BigDecimal.ZERO);
        pedidoEmEntrega.setStatus(PedidoStatus.EM_ENTREGA);
        pedidoEmEntrega.setItens(Collections.singletonList(item));

        List<Pedido> pedidos = List.of(pedidoNovo, pedidoConfirmado, pedidoEmPreparo, pedidoEmEntrega);
        when(pedidoService.getAll()).thenReturn(pedidos);
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/atendimento"))
                .andExpectAll(
                        status().isOk(),
                        view().name(VIEW_PEDIDO_ATENDIMENTO_HTML),
                        model().attributeExists(MV_OBJECT_PEDIDOS_NOVOS),
                        model().attributeExists(MV_OBJECT_PEDIDOS_CONFIRMADOS),
                        model().attributeExists(MV_OBJECT_PEDIDOS_EM_PREPARO),
                        model().attributeExists(MV_OBJECT_PEDIDOS_EM_ENTREGA),
                        model().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        model().attribute(MV_OBJECT_PEDIDOS_NOVOS, List.of(PedidoResponse.fromEntity(pedidoNovo))),
                        model().attribute(MV_OBJECT_PEDIDOS_CONFIRMADOS, List.of(PedidoResponse.fromEntity(pedidoConfirmado))),
                        model().attribute(MV_OBJECT_PEDIDOS_EM_PREPARO, List.of(PedidoResponse.fromEntity(pedidoEmPreparo))),
                        model().attribute(MV_OBJECT_PEDIDOS_EM_ENTREGA, List.of(PedidoResponse.fromEntity(pedidoEmEntrega))),
                        model().attribute(MV_OBJECT_CURRENT_PAGE, PEDIDOS)
                );
    }

    private void testarAtualizacaoDeStatus(String url, PedidoStatus statusEsperado) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setTelefone("11999998888");
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>());
        pedido.setCliente(cliente);
        pedido.setStatus(PedidoStatus.NOVO);

        doNothing().when(pedidoService).updateStatus(1L, statusEsperado);
        when(pedidoService.getAll()).thenReturn(Collections.singletonList(pedido));

        mockMvc.perform(MockMvcRequestBuilders.get(url).param("id", "1")
                        .with(csrf()))
                .andExpectAll(
                        status().isFound(),
                        view().name(VIEW_REDIRECT_ATENDIMENTO),
                        flash().attributeExists(MV_OBJECT_PEDIDOS_NOVOS),
                        flash().attributeExists(MV_OBJECT_CURRENT_PAGE),
                        flash().attribute(MV_OBJECT_PEDIDOS_NOVOS, List.of(PedidoResponse.fromEntity(pedido))),
                        flash().attribute(MV_OBJECT_CURRENT_PAGE, PEDIDOS)
                );
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testCancelarPedido() throws Exception {
        testarAtualizacaoDeStatus("/pedido/cancelar", PedidoStatus.CANCELADO);
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testConfirmarPedido() throws Exception {
        testarAtualizacaoDeStatus("/pedido/confirmar", PedidoStatus.CONFIRMADO);
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testPrepararPedido() throws Exception {
        testarAtualizacaoDeStatus("/pedido/preparar", PedidoStatus.EM_PREPARO);
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testEntregarPedido() throws Exception {
        testarAtualizacaoDeStatus("/pedido/entregar", PedidoStatus.EM_ENTREGA);
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, roles = {ADMIN_ROLE})
    void testFinalizarPedido() throws Exception {
        testarAtualizacaoDeStatus("/pedido/finalizar", PedidoStatus.FINALIZADO);
    }
}