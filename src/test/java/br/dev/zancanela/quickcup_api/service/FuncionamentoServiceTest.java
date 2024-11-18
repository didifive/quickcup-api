package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo.ABERTO;
import static br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo.FECHADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FuncionamentoServiceTest {

    @Mock
    private FuncionamentoRepository funcionamentoRepository;

    @Mock
    private FuncionamentoEspecialService funcionamentoEspecialService;

    @InjectMocks
    private FuncionamentoService funcionamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFuncionamento() {
        Funcionamento novoFuncionamento = new Funcionamento();
        novoFuncionamento.setDiaSemana(DiaSemana.SEGUNDA);
        novoFuncionamento.setHoraInicio(Time.valueOf(LocalTime.of(8, 0)));
        novoFuncionamento.setHoraFim(Time.valueOf(LocalTime.of(18, 0)));

        when(funcionamentoRepository.save(any(Funcionamento.class))).thenReturn(novoFuncionamento);
        when(funcionamentoRepository.findById(any())).thenReturn(Optional.of(novoFuncionamento));

        Funcionamento createdFuncionamento = funcionamentoService.create(novoFuncionamento);

        assertThat(createdFuncionamento).isNotNull();
        assertThat(createdFuncionamento.getHoraInicio()).isEqualTo(Time.valueOf(LocalTime.of(8, 0)));
        assertThat(createdFuncionamento.getHoraFim()).isEqualTo(Time.valueOf(LocalTime.of(18, 0)));
    }

    @Test
    void testCreateFuncionamentoWithInvalidTimes() {
        Funcionamento novoFuncionamento = new Funcionamento();
        novoFuncionamento.setDiaSemana(DiaSemana.SEGUNDA);
        novoFuncionamento.setHoraInicio(Time.valueOf(LocalTime.of(18, 0)));
        novoFuncionamento.setHoraFim(Time.valueOf(LocalTime.of(8, 0)));

        assertThatThrownBy(() -> funcionamentoService.create(novoFuncionamento))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("O horário de abertura deve ser menor que o horário de fechamento");
    }

    @Test
    void testGetByIdFound() {
        Funcionamento funcionamento = new Funcionamento();
        funcionamento.setDiaSemana(DiaSemana.SEGUNDA);
        funcionamento.setHoraInicio(Time.valueOf(LocalTime.of(8, 0)));
        funcionamento.setHoraFim(Time.valueOf(LocalTime.of(18, 0)));

        when(funcionamentoRepository.findById(any())).thenReturn(Optional.of(funcionamento));

        Funcionamento foundFuncionamento = funcionamentoService.getById(DiaSemana.SEGUNDA);

        assertThat(foundFuncionamento).isNotNull();
        assertThat(foundFuncionamento.getDiaSemana()).isEqualTo(DiaSemana.SEGUNDA);
    }

    @Test
    void testGetByIdNotFound() {
        when(funcionamentoRepository.findById(any())).thenReturn(Optional.empty());

        Funcionamento foundFuncionamento = funcionamentoService.getById(DiaSemana.SEGUNDA);

        assertThat(foundFuncionamento).isNotNull();
        assertThat(foundFuncionamento.getDiaSemana()).isEqualTo(DiaSemana.SEGUNDA);
    }

    @Test
    void testGetAll() {
        List<Funcionamento> funcionamentos = new ArrayList<>();
        for (DiaSemana dia : DiaSemana.values()) {
            Funcionamento funcionamento = new Funcionamento();
            funcionamento.setDiaSemana(dia);
            funcionamentos.add(funcionamento);
        }

        when(funcionamentoRepository.findById(any())).thenAnswer(invocation -> {
            DiaSemana dia = invocation.getArgument(0);
            return Optional.of(new Funcionamento(dia));
        });

        List<Funcionamento> foundFuncionamentos = funcionamentoService.getAll();

        assertThat(foundFuncionamentos).isNotEmpty();
        assertThat(foundFuncionamentos).hasSize(DiaSemana.values().length);
    }

    @Test
    void testDeleteFuncionamento() {
        Funcionamento funcionamento = new Funcionamento();
        funcionamento.setDiaSemana(DiaSemana.SEGUNDA);

        when(funcionamentoRepository.findById(any())).thenReturn(Optional.of(funcionamento));

        funcionamentoService.delete(DiaSemana.SEGUNDA);

        verify(funcionamentoRepository, times(1)).delete(funcionamento);
    }

    @Test
    void testIsOpenRegularHours() {
        Funcionamento funcionamento = new Funcionamento();
        funcionamento.setDiaSemana(DiaSemana.SEGUNDA);
        funcionamento.setHoraInicio(Time.valueOf(LocalTime.now().minusHours(1)));
        funcionamento.setHoraFim(Time.valueOf(LocalTime.now().plusHours(1)));

        when(funcionamentoRepository.findById(any())).thenReturn(Optional.of(funcionamento));
        when(funcionamentoEspecialService.getFuncionamentoEspecialAtivo()).thenReturn(null);

        boolean isOpen = funcionamentoService.isOpen();

        assertThat(isOpen).isTrue();
    }

    @Test
    void testIsOpenSpecialHoursClosed() {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setTipo(FECHADO);

        when(funcionamentoEspecialService.getFuncionamentoEspecialAtivo()).thenReturn(funcionamentoEspecial);

        boolean isOpen = funcionamentoService.isOpen();

        assertThat(isOpen).isFalse();
    }

    @Test
    void testIsOpenSpecialHoursOpen() {
        FuncionamentoEspecial funcionamentoEspecial = new FuncionamentoEspecial();
        funcionamentoEspecial.setTipo(ABERTO);

        when(funcionamentoEspecialService.getFuncionamentoEspecialAtivo()).thenReturn(funcionamentoEspecial);

        boolean isOpen = funcionamentoService.isOpen();

        assertThat(isOpen).isTrue();
    }
}
