package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
    List<Promocao> findAllByProdutoId(Long id);
}
