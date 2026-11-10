package com.inventario.controller;

import com.inventario.service.NotificacionService;
import com.inventario.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/* HU7: alertas de stock bajo y HU10: sección de notificaciones */
@Controller
@RequestMapping("/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final ProductoService productoService;

    public NotificacionController(NotificacionService notificacionService, ProductoService productoService) {
        this.notificacionService = notificacionService;
        this.productoService = productoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("notificaciones", notificacionService.getNotificaciones());
        model.addAttribute("productosStockBajo", productoService.getStockBajo());
        /* Al abrir la sección, las notificaciones quedan como leídas (la campana vuelve a 0) */
        notificacionService.marcarTodasLeidas();
        model.addAttribute("notificacionesNoLeidas", 0L);
        return "notificacion/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Integer idNotificacion) {
        notificacionService.eliminar(idNotificacion);
        return "redirect:/notificacion/listado";
    }

    /* Botón "Limpiar todo" */
    @PostMapping("/limpiar")
    public String limpiar() {
        notificacionService.limpiar();
        return "redirect:/notificacion/listado";
    }
}
