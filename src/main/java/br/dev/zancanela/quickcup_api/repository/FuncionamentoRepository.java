package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Funcionamento;
import br.dev.zancanela.quickcup_api.entity.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionamentoRepository extends JpaRepository<Funcionamento, Long> {
    Optional<Funcionamento> findByDiaSemana(DiaSemana diaSemana);
}
