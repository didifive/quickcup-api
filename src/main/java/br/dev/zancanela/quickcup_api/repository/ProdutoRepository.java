package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByEnabledTrue();
    List<Produto> findAllByGrupoId(Long id);
    List<Produto> findAllByGrupoIdAndEnabledTrue(Long id);
    Optional<Produto> findByIdAndEnabledTrue(Long id);
}
