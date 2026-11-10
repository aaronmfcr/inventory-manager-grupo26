package com.inventario.service;

import com.inventario.domain.Notificacion;
import com.inventario.domain.Producto;
import com.inventario.repository.NotificacionRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU7 (alertas de stock bajo) y HU10 (sección de notificaciones) */
@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Notificacion> getNotificaciones() {
        return notificacionRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public long getNoLeidas() {
        return notificacionRepository.countByLeidaFalse();
    }

    @Transactional
    public void marcarTodasLeidas() {
        notificacionRepository.marcarTodasLeidas();
    }

    @Transactional
    public void eliminar(Integer idNotificacion) {
        notificacionRepository.deleteById(idNotificacion);
    }

    /* "Limpiar todo" */
    @Transactional
    public void limpiar() {
        notificacionRepository.deleteAll();
    }

    @Transactional
    public void crear(String tipo, String referencia, Integer cantidad) {
        var notificacion = new Notificacion();
        notificacion.setTipo(tipo);
        notificacion.setReferencia(referencia);
        notificacion.setCantidad(cantidad);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionRepository.save(notificacion);
    }

    /* HU7: revisa el stock de un producto y genera la alerta si está por debajo del mínimo */
    @Transactional
    public void revisarStock(Producto producto) {
        String estado = producto.getEstadoStock();
        if ("EN_STOCK".equals(estado)) {
            return;
        }
        String tipo = "SIN_STOCK".equals(estado) ? Notificacion.SIN_STOCK : Notificacion.STOCK_BAJO;
        if (!notificacionRepository.existsByTipoAndReferenciaAndLeidaFalse(tipo, producto.getNombre())) {
            crear(tipo, producto.getNombre(), producto.getExistencias());
        }
    }
}
