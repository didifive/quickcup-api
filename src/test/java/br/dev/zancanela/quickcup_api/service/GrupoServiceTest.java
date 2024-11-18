package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Grupo;
import br.dev.zancanela.quickcup_api.entity.Produto;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.GrupoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GrupoServiceTest {

    @Mock
    private GrupoRepository grupoRepository;

    @InjectMocks
    private GrupoService grupoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGrupo() {
        Grupo novoGrupo = new Grupo();
        novoGrupo.setNome("Novo Grupo");

        when(grupoRepository.save(any(Grupo.class))).thenReturn(novoGrupo);
        when(grupoRepository.findByNome(anyString())).thenReturn(Optional.empty());

        Grupo createdGrupo = grupoService.create(novoGrupo);

        assertThat(createdGrupo).isNotNull();
        assertThat(createdGrupo.getNome()).isEqualTo("Novo Grupo");
    }

    @Test
    void testCreateGrupoWithExistingName() {
        Grupo novoGrupo = new Grupo();
        novoGrupo.setNome("Grupo Existente");

        when(grupoRepository.findByNome(anyString())).thenReturn(Optional.of(novoGrupo));

        assertThatThrownBy(() -> grupoService.create(novoGrupo))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Já existe um grupo o nome [Grupo Existente]");
    }

    @Test
    void testGetByIdFound() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setNome("Grupo Teste");

        when(grupoRepository.findById(anyLong())).thenReturn(Optional.of(grupo));

        Grupo foundGrupo = grupoService.getById(1L);

        assertThat(foundGrupo).isNotNull();
        assertThat(foundGrupo.getId()).isEqualTo(1L);
        assertThat(foundGrupo.getNome()).isEqualTo("Grupo Teste");
    }

    @Test
    void testGetByIdNotFound() {
        when(grupoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> grupoService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Grupo não encontrado");
    }

    @Test
    void testUpdateGrupo() {
        Grupo grupoExistente = new Grupo();
        grupoExistente.setId(1L);
        grupoExistente.setNome("Grupo Antigo");

        Grupo novoGrupo = new Grupo();
        novoGrupo.setNome("Grupo Atualizado");
        novoGrupo.setDescricao("Descricao Atualizada");

        when(grupoRepository.findById(anyLong())).thenReturn(Optional.of(grupoExistente));
        when(grupoRepository.findByNome(anyString())).thenReturn(Optional.empty());
        when(grupoRepository.save(any(Grupo.class))).thenReturn(grupoExistente);

        Grupo updatedGrupo = grupoService.update(1L, novoGrupo);

        assertThat(updatedGrupo).isNotNull();
        assertThat(updatedGrupo.getNome()).isEqualTo("Grupo Atualizado");
        assertThat(updatedGrupo.getDescricao()).isEqualTo("Descricao Atualizada");
    }

    @Test
    void testUpdateGrupoWithExistingName() {
        Grupo grupoExistente = new Grupo();
        grupoExistente.setId(1L);
        grupoExistente.setNome("Grupo Antigo");

        Grupo novoGrupo = new Grupo();
        novoGrupo.setNome("Grupo Existente");

        when(grupoRepository.findById(anyLong())).thenReturn(Optional.of(grupoExistente));
        when(grupoRepository.findByNome(anyString())).thenReturn(Optional.of(novoGrupo));

        assertThatThrownBy(() -> grupoService.update(1L, novoGrupo))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Já existe um grupo o nome [Grupo Existente]");
    }

    @Test
    void testGetAll() {
        List<Grupo> grupos = Collections.singletonList(new Grupo());
        when(grupoRepository.findAll()).thenReturn(grupos);

        List<Grupo> foundGrupos = grupoService.getAll();

        assertThat(foundGrupos).isNotEmpty();
    }

    @Test
    void testDeleteGrupoWithoutProdutos() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setProdutos(Collections.emptyList());

        when(grupoRepository.findById(anyLong())).thenReturn(Optional.of(grupo));

        grupoService.delete(1L);

        verify(grupoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteGrupoWithProdutos() {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setProdutos(List.of(new Produto())); // Supondo que Produto é uma classe relacionada

        when(grupoRepository.findById(anyLong())).thenReturn(Optional.of(grupo));

        assertThatThrownBy(() -> grupoService.delete(1L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Grupo possui produtos cadastrados");
    }
}
