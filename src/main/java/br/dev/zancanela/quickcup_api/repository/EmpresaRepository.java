package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Short> {
}
