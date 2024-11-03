package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static br.dev.zancanela.quickcup_api.entity.enums.FunctiomentoEspecialTipo.ABERTO;
import static br.dev.zancanela.quickcup_api.entity.enums.FunctiomentoEspecialTipo.FECHADO;

@Service
public class FuncionamentoService {

    private final FuncionamentoRepository repository;
    private final FuncionamentoEspecialService funcionamentoEspecialService;


    public FuncionamentoService(
            FuncionamentoRepository repository,
            FuncionamentoEspecialService funcionamentoEspecialService) {
        this.repository = repository;
        this.funcionamentoEspecialService = funcionamentoEspecialService;
    }

    @Transactional
    public Funcionamento create(Funcionamento novoFuncionamento) {
        Funcionamento funcionamentoExistente = getByIdDiaSemana(novoFuncionamento.getDiaSemana());

        funcionamentoExistente.setDiaSemana(novoFuncionamento.getDiaSemana());
        funcionamentoExistente.setHoraInicio(novoFuncionamento.getHoraInicio());
        funcionamentoExistente.setHoraFim(novoFuncionamento.getHoraFim());

        return repository.save(novoFuncionamento);
    }

    @Transactional
    public Funcionamento update(Long id, Funcionamento novoFuncionamento) {
        Funcionamento funcionamentoExistente = this.getById(id);

        funcionamentoExistente.setDiaSemana(novoFuncionamento.getDiaSemana());
        funcionamentoExistente.setHoraInicio(novoFuncionamento.getHoraInicio());
        funcionamentoExistente.setHoraFim(novoFuncionamento.getHoraFim());

        return repository.save(funcionamentoExistente);
    }

    public Funcionamento getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionamento não encontrado"));
    }

    public Funcionamento getByIdDiaSemana(DiaSemana diaSemana) {
        return repository.findByDiaSemana(diaSemana)
                .orElseGet(Funcionamento::new);
    }

    @Transactional
    public void delete(Long id) {
        Funcionamento funcionamento = this.getById(id);
        repository.delete(funcionamento);
    }

    public boolean isOpen() {
        DiaSemana currentDay = DiaSemana.from(LocalDate.now().getDayOfWeek());
        Funcionamento regularHours = getByIdDiaSemana(currentDay);
        FuncionamentoEspecial specialHours = funcionamentoEspecialService.getFuncionamentoEspecialAtivo();

        LocalTime currentTime = LocalTime.now();
        LocalTime openTime = regularHours.getHoraInicio().toLocalTime();
        LocalTime closeTime = regularHours.getHoraFim().toLocalTime();

        boolean isWithinRegularHours = currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);

        return (specialHours == null || specialHours.getTipo() != FECHADO) && isWithinRegularHours
                || specialHours != null && specialHours.getTipo() == ABERTO;
    }

}

