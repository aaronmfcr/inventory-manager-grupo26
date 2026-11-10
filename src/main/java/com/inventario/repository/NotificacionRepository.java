package com.inventario.repository;

import com.inventario.domain.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    /* HU10: todas las notificaciones, la más reciente primero */
    List<Notificacion> findAllByOrderByFechaDesc();

    /* Cantidad de notificaciones sin leer (número de la campana) */
    long countByLeidaFalse();

    /* Evita repetir la misma alerta mientras no se haya leído */
    boolean existsByTipoAndReferenciaAndLeidaFalse(String tipo, String referencia);

    @Modifying
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.leida = false")
    int marcarTodasLeidas();
}
