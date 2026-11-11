package com.inventario.repository;

import com.inventario.domain.Venta;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    /* HU6: historial de ventas, la más reciente primero */
    List<Venta> findAllByOrderByFechaDesc();

    /* Total de ingresos para la página de inicio (consulta JPQL) */
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v")
    BigDecimal totalIngresos();
}
