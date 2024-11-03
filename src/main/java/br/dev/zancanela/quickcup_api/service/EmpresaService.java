package br.dev.zancanela.quickcup_api.service;

import br.dev.zancanela.quickcup_api.entity.Empresa;
import br.dev.zancanela.quickcup_api.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Empresa update(Empresa novosDados) {
        return repository.save(novosDados);
    }
}
