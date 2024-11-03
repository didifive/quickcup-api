package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import br.dev.zancanela.quickcup_api.exception.DataIntegrityViolationException;
import br.dev.zancanela.quickcup_api.exception.EntityNotFoundException;
import br.dev.zancanela.quickcup_api.repository.FuncionamentoEspecialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionamentoEspecialService {

    private final FuncionamentoEspecialRepository repository;

    public FuncionamentoEspecialService(FuncionamentoEspecialRepository repository) {
        this.repository = repository;
    }

    public FuncionamentoEspecial create(FuncionamentoEspecial novoFuncionamento) {
        verificaSobreposicao(novoFuncionamento);

        return repository.save(novoFuncionamento);
    }

    private void verificaSobreposicao(FuncionamentoEspecial novoFuncionamento) {
        List<FuncionamentoEspecial> listaFuncionamento = this.getAll();

        listaFuncionamento.forEach(f -> {
            if (f.getDataInicio().isBefore(novoFuncionamento.getDataFim())
                    || f.getDataFim().isAfter(novoFuncionamento.getDataInicio())) {
                throw new DataIntegrityViolationException("Já existe um funcionamento especial que sobrepõe");
            }
        });
    }

    public FuncionamentoEspecial getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionamento especial não encontrado"));
    }

    public List<FuncionamentoEspecial> getAll() {
        return repository.findAll();
    }

    public FuncionamentoEspecial getFuncionamentoEspecialAtivo() {
        return repository.findFuncionamentoEspecialAtivo().orElse(null);
    }

    public void delete(Long id) {
        FuncionamentoEspecial existente = this.getById(id);
        repository.delete(existente);
    }
}
