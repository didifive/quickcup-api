package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findAllByProdutoId(Long id);
}
