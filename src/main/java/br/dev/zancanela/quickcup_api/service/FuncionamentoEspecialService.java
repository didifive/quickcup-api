package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoEspecialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionamentoEspecialService {

    private final FuncionamentoEspecialRepository repository;

    public FuncionamentoEspecialService(FuncionamentoEspecialRepository repository) {
        this.repository = repository;
    }

    public FuncionamentoEspecial create(FuncionamentoEspecial novoFuncionamento) {
        validaDatas(novoFuncionamento);

        return repository.save(novoFuncionamento);
    }

    private void validaDatas(FuncionamentoEspecial novoFuncionamento) {
        List<FuncionamentoEspecial> listaFuncionamento = this.getAll();

        if(novoFuncionamento.getDataInicio().equals(novoFuncionamento.getDataFim()) ||
                novoFuncionamento.getDataInicio().isAfter(novoFuncionamento.getDataFim())) {
            throw new DataIntegrityViolationException("Data e hora de início não pode ser igual ou maior que a data e hora fim");
        }

        listaFuncionamento.forEach(f -> {
            if (f.getId().equals(novoFuncionamento.getId())) {
                return;
            }
            if (novoFuncionamento.getDataInicio().equals(f.getDataInicio())
                    || novoFuncionamento.getDataFim().equals(f.getDataFim())
                    || (novoFuncionamento.getDataInicio().isBefore(f.getDataFim())
                            && novoFuncionamento.getDataFim().isAfter(f.getDataInicio()))) {
                throw new DataIntegrityViolationException("Já existe um funcionamento especial que sobrepõe");
            }
        });
    }

    public FuncionamentoEspecial getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionamento especial não encontrado"));
    }

    public List<FuncionamentoEspecial> getAll() {
        return repository.findAllByOrderByDataInicioAsc();
    }

    public List<FuncionamentoEspecial> getTop5Futuro() {
        return repository.findTop5Futuro();
    }

    public FuncionamentoEspecial getFuncionamentoEspecialAtivo() {
        return repository.findFuncionamentoEspecialAtivo().orElse(null);
    }

    public void delete(Long id) {
        FuncionamentoEspecial existente = this.getById(id);
        repository.delete(existente);
    }
}
