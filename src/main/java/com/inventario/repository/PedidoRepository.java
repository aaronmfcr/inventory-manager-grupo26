package com.inventario.repository;

import com.inventario.domain.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    /* HU13: estatus de pedidos, el más reciente primero */
    List<Pedido> findAllByOrderByFechaDesc();

    long countByEstado(String estado);
}
