package br.dev.zancanela.quickcup_api.controller.admin;

import br.dev.zancanela.quickcup_api.dto.admin.response.PedidoResponse;
import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Controller
@RequestMapping("/pedido")
public class PedidoController {


    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/atendimento")
    public ModelAndView listaPedidos() {

        ModelAndView mv = new ModelAndView(VIEW_PEDIDO_ATENDIMENTO_HTML);

        List<Pedido> pedidos = pedidoService.getAll();

        List<PedidoResponse> listaPedidosNovos =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.NOVO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosConfirmados =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.CONFIRMADO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosEmAndamento =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.EM_PREPARO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosEmEntrega =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.EM_ENTREGA))
                        .map(PedidoResponse::fromEntity).toList();

        mv.addObject(MV_OBJECT_PEDIDOS_NOVOS, listaPedidosNovos);
        mv.addObject(MV_OBJECT_PEDIDOS_CONFIRMADOS, listaPedidosConfirmados);
        mv.addObject(MV_OBJECT_PEDIDOS_EM_PREPARO, listaPedidosEmAndamento);
        mv.addObject(MV_OBJECT_PEDIDOS_EM_ENTREGA, listaPedidosEmEntrega);
        mv.addObject(MV_OBJECT_CURRENT_PAGE, PEDIDOS);

        return mv;

    }

    @GetMapping("/cancelar")
    public ModelAndView cancelarPedido(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes) {

        return atualizaStatusEListaPedidos(
                id,
                PedidoStatus.CANCELADO,
                redirectAttributes);

    }

    @GetMapping("/confirmar")
    public ModelAndView confirmarPedido(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes
    ) {

        return atualizaStatusEListaPedidos(
                id,
                PedidoStatus.CONFIRMADO,
                redirectAttributes);

    }

    @GetMapping("/preparar")
    public ModelAndView prepararPedido(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes) {

        return atualizaStatusEListaPedidos(
                id,
                PedidoStatus.EM_PREPARO,
                redirectAttributes);

    }

    @GetMapping("/entregar")
    public ModelAndView entregarPedido(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes) {

        return atualizaStatusEListaPedidos(
                id,
                PedidoStatus.EM_ENTREGA,
                redirectAttributes);

    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarPedido(
            @RequestParam Long id
            , RedirectAttributes redirectAttributes) {

        return atualizaStatusEListaPedidos(
                id,
                PedidoStatus.FINALIZADO,
                redirectAttributes);

    }

    private ModelAndView atualizaStatusEListaPedidos(
            Long id,
            PedidoStatus status,
            RedirectAttributes redirectAttributes
    ) {

        try {
            pedidoService.updateStatus(id, status);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    MV_OBJECT_MENSAGEM_ERRO,
                    e.getMessage());
        }

        List<Pedido> pedidos = pedidoService.getAll();

        List<PedidoResponse> listaPedidosNovos =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.NOVO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosConfirmados =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.CONFIRMADO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosEmAndamento =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.EM_PREPARO))
                        .map(PedidoResponse::fromEntity).toList();
        List<PedidoResponse> listaPedidosEmEntrega =
                pedidos.stream()
                        .filter(pedido -> pedido.getStatus().equals(PedidoStatus.EM_ENTREGA))
                        .map(PedidoResponse::fromEntity).toList();

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_PEDIDOS_NOVOS
                , listaPedidosNovos);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_PEDIDOS_CONFIRMADOS
                , listaPedidosConfirmados);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_PEDIDOS_EM_PREPARO
                , listaPedidosEmAndamento);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_PEDIDOS_EM_ENTREGA
                , listaPedidosEmEntrega);

        redirectAttributes.addFlashAttribute(
                MV_OBJECT_CURRENT_PAGE
                , PEDIDOS);

        return new ModelAndView(VIEW_REDIRECT_ATENDIMENTO);

    }

}