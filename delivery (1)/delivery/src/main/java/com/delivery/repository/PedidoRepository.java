package com.delivery.repository;

import com.delivery.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByNomeClienteContainingIgnoreCase(String nomeCliente);
    List<Pedido> findByStatus(Pedido.StatusPedido status);
}
