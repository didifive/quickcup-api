package br.dev.zancanela.quickcup_api.repository;

import br.dev.zancanela.quickcup_api.entity.Pedido;
import br.dev.zancanela.quickcup_api.entity.enums.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByStatus(PedidoStatus status);

    List<Pedido> findAllByCliendId(Long clienteId);
}
