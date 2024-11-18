package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.pk.ItemPedidoId;
import br.dev.zancanela.quickcup_api.repository.ItemPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllByProdutoId() {
        Long produtoId = 1L;
        ItemPedidoId itemPedidoId = new ItemPedidoId();
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(itemPedidoId);

        when(itemPedidoRepository.findAllByIdProdutoId(anyLong())).thenReturn(Collections.singletonList(itemPedido));

        List<ItemPedido> result = itemPedidoService.getAllByProdutoId(produtoId);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(itemPedidoId);
    }

    @Test
    void testGetAllByProdutoIdEmpty() {
        Long produtoId = 1L;

        when(itemPedidoRepository.findAllByIdProdutoId(anyLong())).thenReturn(Collections.emptyList());

        List<ItemPedido> result = itemPedidoService.getAllByProdutoId(produtoId);

        assertThat(result).isEmpty();
    }
}
