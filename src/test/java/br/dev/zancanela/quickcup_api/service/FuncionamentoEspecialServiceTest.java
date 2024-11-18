package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoEspecialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FuncionamentoEspecialServiceTest {

    @Mock
    private FuncionamentoEspecialRepository funcionamentoEspecialRepository;

    @InjectMocks
    private FuncionamentoEspecialService funcionamentoEspecialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFuncionamentoEspecial() {
        FuncionamentoEspecial novoFuncionamento = new FuncionamentoEspecial();
        novoFuncionamento.setDataInicio(Instant.now().plusSeconds(60 * 60 * 24));
        novoFuncionamento.setDataFim(Instant.now().plusSeconds(60 * 60 * 24 * 2));

        when(funcionamentoEspecialRepository.save(any(FuncionamentoEspecial.class))).thenReturn(novoFuncionamento);
        when(funcionamentoEspecialRepository.findAllByOrderByDataInicioAsc()).thenReturn(Collections.emptyList());

        FuncionamentoEspecial createdFuncionamento = funcionamentoEspecialService.create(novoFuncionamento);

        assertThat(createdFuncionamento).isNotNull();
    }

    @Test
    void testCreateFuncionamentoEspecialWithInvalidDates() {
        FuncionamentoEspecial novoFuncionamento = new FuncionamentoEspecial();
        novoFuncionamento.setDataInicio(Instant.now().plusSeconds(60 * 60 * 24 * 2));
        novoFuncionamento.setDataFim(Instant.now().plusSeconds(60 * 60 * 24));

        assertThatThrownBy(() -> funcionamentoEspecialService.create(novoFuncionamento))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Data e hora de início não pode ser igual ou maior que a data e hora fim");
    }

    @Test
    void testGetByIdFound() {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setId(1L);

        when(funcionamentoEspecialRepository.findById(anyLong())).thenReturn(Optional.of(funcionamentoEspecial));

        FuncionamentoEspecial foundFuncionamentoEspecial = funcionamentoEspecialService.getById(1L);

        assertThat(foundFuncionamentoEspecial).isNotNull();
        assertThat(foundFuncionamentoEspecial.getId()).isEqualTo(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(funcionamentoEspecialRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> funcionamentoEspecialService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Funcionamento especial não encontrado");
    }

    @Test
    void testGetAll() {
        List<FuncionamentoEspecial> funcionamentoEspeciais = Collections.singletonList(new FuncionamentoEspecial());
        when(funcionamentoEspecialRepository.findAllByOrderByDataInicioAsc()).thenReturn(funcionamentoEspeciais);

        List<FuncionamentoEspecial> foundFuncionamentoEspeciais = funcionamentoEspecialService.getAll();

        assertThat(foundFuncionamentoEspeciais).isNotEmpty();
    }

    @Test
    void testGetTop5Futuro() {
        List<FuncionamentoEspecial> funcionamentoEspeciais = Collections.singletonList(new FuncionamentoEspecial());
        when(funcionamentoEspecialRepository.findTop5Futuro()).thenReturn(funcionamentoEspeciais);

        List<FuncionamentoEspecial> foundFuncionamentoEspeciais = funcionamentoEspecialService.getTop5Futuro();

        assertThat(foundFuncionamentoEspeciais).isNotEmpty();
    }

    @Test
    void testGetFuncionamentoEspecialAtivo() {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();

        when(funcionamentoEspecialRepository.findFuncionamentoEspecialAtivo()).thenReturn(Optional.of(funcionamentoEspecial));

        FuncionamentoEspecial foundFuncionamentoEspecial = funcionamentoEspecialService.getFuncionamentoEspecialAtivo();

        assertThat(foundFuncionamentoEspecial).isNotNull();
    }

    @Test
    void testGetFuncionamentoEspecialAtivoNull() {
        when(funcionamentoEspecialRepository.findFuncionamentoEspecialAtivo()).thenReturn(Optional.empty());

        FuncionamentoEspecial foundFuncionamentoEspecial = funcionamentoEspecialService.getFuncionamentoEspecialAtivo();

        assertThat(foundFuncionamentoEspecial).isNull();
    }

    @Test
    void testDeleteFuncionamentoEspecial() {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setId(1L);

        when(funcionamentoEspecialRepository.findById(anyLong())).thenReturn(Optional.of(funcionamentoEspecial));

        funcionamentoEspecialService.delete(1L);

        verify(funcionamentoEspecialRepository, times(1)).delete(funcionamentoEspecial);
    }
}
