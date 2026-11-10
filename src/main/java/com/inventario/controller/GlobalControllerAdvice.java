package com.inventario.controller;

import com.inventario.service.NotificacionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/* Datos que necesitan todas las páginas (barra superior de la plantilla) */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final NotificacionService notificacionService;

    public GlobalControllerAdvice(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /* Número que aparece en la campana de notificaciones */
    @ModelAttribute("notificacionesNoLeidas")
    public long notificacionesNoLeidas() {
        return notificacionService.getNoLeidas();
    }

    /* Ruta actual: se usa para marcar el menú activo y para cambiar de idioma sin salir de la página */
    @ModelAttribute("urlActual")
    public String urlActual(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
