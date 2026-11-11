package com.inventario.controller;

import com.inventario.service.ProductoService;
import com.inventario.service.VentaService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU6: Registro de ventas */
@Controller
@RequestMapping("/venta")
public class VentaController {

    private final VentaService ventaService;
    private final ProductoService productoService;
    private final MessageSource messageSource;

    public VentaController(VentaService ventaService, ProductoService productoService,
            MessageSource messageSource) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("ventas", ventaService.getVentas());
        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("totalIngresos", ventaService.getTotalIngresos());
        return "venta/listado";
    }

    @PostMapping("/registrar")
    public String registrar(Integer idProducto, Integer cantidad, RedirectAttributes redirectAttributes,
            Locale locale) {
        try {
            ventaService.registrar(idProducto, cantidad);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("venta.registrada", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/venta/listado";
    }

    private String mensaje(String llave, Locale locale) {
        return messageSource.getMessage(llave, null, llave, locale);
    }
}
