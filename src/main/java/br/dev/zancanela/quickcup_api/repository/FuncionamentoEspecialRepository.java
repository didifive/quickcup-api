package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.FuncionamentoEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionamentoEspecialRepository extends JpaRepository<FuncionamentoEspecial, Long> {

    @Query("SELECT fe " +
            "FROM FuncionamentoEspecial fe " +
            "WHERE fe.dataInicio <= CURRENT_TIMESTAMP " +
            "AND fe.dataFim >= CURRENT_TIMESTAMP " +
            "ORDER BY fe.dataInicio DESC LIMIT 1")
    Optional<FuncionamentoEspecial> findFuncionamentoEspecialAtivo();
}
