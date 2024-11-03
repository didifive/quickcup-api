package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByGrupoId(Long id);
}
