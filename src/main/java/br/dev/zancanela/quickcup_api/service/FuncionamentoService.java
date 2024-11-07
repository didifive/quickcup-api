package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static br.dev.zancanela.quickcup_api.entity.enums.DiaSemana.*;
import static br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo.ABERTO;
import static br.dev.zancanela.quickcup_api.entity.enums.FuncionamentoEspecialTipo.FECHADO;

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
        Funcionamento funcionamentoExistente = getById(novoFuncionamento.getDiaSemana());

        funcionamentoExistente.setHoraInicio(novoFuncionamento.getHoraInicio());
        funcionamentoExistente.setHoraFim(novoFuncionamento.getHoraFim());

        return repository.save(novoFuncionamento);
    }

    @Transactional
    public Funcionamento update(DiaSemana id, Funcionamento novoFuncionamento) {
        Funcionamento funcionamentoExistente = this.getById(id);

        funcionamentoExistente.setDiaSemana(novoFuncionamento.getDiaSemana());
        funcionamentoExistente.setHoraInicio(novoFuncionamento.getHoraInicio());
        funcionamentoExistente.setHoraFim(novoFuncionamento.getHoraFim());

        return repository.save(funcionamentoExistente);
    }

    public Funcionamento getById(DiaSemana id) {
        return repository.findById(id)
                .orElse(new Funcionamento(id));
    }

    public List<Funcionamento> getAll() {
        List<Funcionamento> lista = new ArrayList<>();

        lista.add(getById(SEGUNDA));
        lista.add(getById(TERCA));
        lista.add(getById(QUARTA));
        lista.add(getById(QUINTA));
        lista.add(getById(SEXTA));
        lista.add(getById(SABADO));
        lista.add(getById(DOMINGO));

        return lista;
    }

    @Transactional
    public void delete(DiaSemana id) {
        Funcionamento funcionamento = this.getById(id);
        repository.delete(funcionamento);
    }

    public boolean isOpen() {
        DiaSemana currentDay = DiaSemana.from(LocalDate.now().getDayOfWeek());
        Funcionamento regularHours = getById(currentDay);
        FuncionamentoEspecial specialHours = funcionamentoEspecialService.getFuncionamentoEspecialAtivo();

        LocalTime currentTime = LocalTime.now();
        LocalTime openTime = regularHours.getHoraInicio().toLocalTime();
        LocalTime closeTime = regularHours.getHoraFim().toLocalTime();

        boolean isWithinRegularHours = currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);

        return (specialHours == null || specialHours.getTipo() != FECHADO) && isWithinRegularHours
                || specialHours != null && specialHours.getTipo() == ABERTO;
    }

}

