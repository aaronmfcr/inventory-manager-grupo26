package com.inventario.service;

import com.inventario.domain.MensajeSoporte;
import com.inventario.domain.Notificacion;
import com.inventario.repository.MensajeSoporteRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* HU13 y HU15: Soporte técnico por mensajes */
@Service
public class SoporteService {

    private final MensajeSoporteRepository mensajeSoporteRepository;
    private final NotificacionService notificacionService;

    public SoporteService(MensajeSoporteRepository mensajeSoporteRepository,
            NotificacionService notificacionService) {
        this.mensajeSoporteRepository = mensajeSoporteRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional
    public void enviar(MensajeSoporte mensaje) {
        mensaje.setFecha(LocalDateTime.now());
        mensajeSoporteRepository.save(mensaje);
        notificacionService.crear(Notificacion.MENSAJE, mensaje.getAsunto(), null);
    }
}
