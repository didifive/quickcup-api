package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private GrupoService grupoService;

    @Mock
    private ItemPedidoService itemPedidoService;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduto() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);

        Produto novoProduto = new Produto();
        novoProduto.setGrupo(grupo);
        novoProduto.setValorOriginal(new BigDecimal("100.00"));
        novoProduto.setValorDesconto(new BigDecimal("50.00"));

        when(grupoService.getById(anyLong())).thenReturn(grupo);
        when(produtoRepository.save(any(Produto.class))).thenReturn(novoProduto);

        Produto createdProduto = produtoService.create(novoProduto);

        assertThat(createdProduto).isNotNull();
        assertThat(createdProduto.getGrupo()).isEqualTo(grupo);
        assertThat(createdProduto.getValorDesconto()).isEqualTo(new BigDecimal("50.00"));
    }

    @Test
    void testCreateProdutoWithInvalidDiscount() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);

        Produto novoProduto = new Produto();
        novoProduto.setGrupo(grupo);
        novoProduto.setValorOriginal(new BigDecimal("50.00"));
        novoProduto.setValorDesconto(new BigDecimal("100.00"));

        when(grupoService.getById(anyLong())).thenReturn(grupo);

        assertThatThrownBy(() -> produtoService.create(novoProduto))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("O valor de desconto deve ser menor que o valor original");
    }

    @Test
    void testGetByIdFound() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));

        Produto foundProduto = produtoService.getById(1L);

        assertThat(foundProduto).isNotNull();
        assertThat(foundProduto.getId()).isEqualTo(1L);
        assertThat(foundProduto.getNome()).isEqualTo("Produto Teste");
    }

    @Test
    void testGetByIdNotFound() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não encontrado");
    }

    @Test
    void testGetByIdAndEnabledFound() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setEnabled(true);

        when(produtoRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.of(produto));

        Produto foundProduto = produtoService.getByIdAndEnabled(1L);

        assertThat(foundProduto).isNotNull();
        assertThat(foundProduto.getId()).isEqualTo(1L);
        assertThat(foundProduto.isEnabled()).isTrue();
    }

    @Test
    void testGetByIdAndEnabledNotFound() {
        when(produtoRepository.findByIdAndEnabledTrue(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.getByIdAndEnabled(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto ativo não encontrado");
    }

    @Test
    void testGetAll() {
        List<Produto> produtos = Collections.singletonList(new Produto());
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> foundProdutos = produtoService.getAll();

        assertThat(foundProdutos).isNotEmpty();
    }

    @Test
    void testGetAllEnabled() {
        List<Produto> produtos = Collections.singletonList(new Produto());
        when(produtoRepository.findAllByEnabledTrue()).thenReturn(produtos);

        List<Produto> foundProdutos = produtoService.getAllEnabled();

        assertThat(foundProdutos).isNotEmpty();
    }

    @Test
    void testGetAllByGrupoId() {
        List<Produto> produtos = Collections.singletonList(new Produto());
        when(produtoRepository.findAllByGrupoId(anyLong())).thenReturn(produtos);

        List<Produto> foundProdutos = produtoService.getAllByGrupoId(1L);

        assertThat(foundProdutos).isNotEmpty();
    }

    @Test
    void testGetAllByGrupoIdAndEnabled() {
        List<Produto> produtos = Collections.singletonList(new Produto());
        when(produtoRepository.findAllByGrupoIdAndEnabledTrue(anyLong())).thenReturn(produtos);

        List<Produto> foundProdutos = produtoService.getAllByGrupoIdAndEnabled(1L);

        assertThat(foundProdutos).isNotEmpty();
    }

    @Test
    void testUpdateProduto() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);

        Produto existente = new Produto();
        existente.setId(1L);

        Produto novoProduto = new Produto();
        novoProduto.setNome("Produto Atualizado");
        novoProduto.setGrupo(grupo);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(existente));
        when(grupoService.getById(anyLong())).thenReturn(grupo);
        when(produtoRepository.save(any(Produto.class))).thenReturn(existente);

        Produto updatedProduto = produtoService.update(1L, novoProduto);

        assertThat(updatedProduto).isNotNull();
        assertThat(updatedProduto.getNome()).isEqualTo("Produto Atualizado");
        assertThat(updatedProduto.getGrupo()).isEqualTo(grupo);
    }

    @Test
    void testEnableProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setEnabled(false);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto enabledProduto = produtoService.enableProduto(1L);

        assertThat(enabledProduto).isNotNull();
        assertThat(enabledProduto.isEnabled()).isTrue();
    }

    @Test
    void testDisableProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setEnabled(true);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto disabledProduto = produtoService.disableProduto(1L);

        assertThat(disabledProduto).isNotNull();
        assertThat(disabledProduto.isEnabled()).isFalse();
    }

    @Test
    void testDeleteProdutoWithoutItemPedidos() {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(itemPedidoService.getAllByProdutoId(anyLong())).thenReturn(Collections.emptyList());

        produtoService.delete(1L);

        verify(produtoRepository, times(1)).delete(produto);
    }

    @Test
    void testDeleteProdutoWithItemPedidos() {
        Produto produto = new Produto();
        produto.setId(1L);

        ItemPedido itemPedido = new ItemPedido();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(itemPedidoService.getAllByProdutoId(anyLong())).thenReturn(Collections.singletonList(itemPedido));

        assertThatThrownBy(() -> produtoService.delete(1L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Este produto possui pedidos e não pode ser apagado");
    }
}
