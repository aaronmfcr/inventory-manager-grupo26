package com.inventario.controller;

import com.inventario.service.PedidoService;
import com.inventario.service.ProductoService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU9: creación de pedidos, HU13: estatus de pedidos y HU8: reposición al completar */
@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProductoService productoService;
    private final MessageSource messageSource;

    public PedidoController(PedidoService pedidoService, ProductoService productoService,
            MessageSource messageSource) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("pedidos", pedidoService.getPedidos());
        model.addAttribute("productos", productoService.getProductos());
        return "pedido/listado";
    }

    @PostMapping("/crear")
    public String crear(Integer idProducto, Integer cantidad, String proveedor,
            RedirectAttributes redirectAttributes, Locale locale) {
        try {
            pedidoService.crear(idProducto, cantidad, proveedor);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("pedido.creado", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/pedido/listado";
    }

    @PostMapping("/completar")
    public String completar(Integer idPedido, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            pedidoService.completar(idPedido);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("pedido.completadoOk", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/pedido/listado";
    }

    @PostMapping("/cancelar")
    public String cancelar(Integer idPedido, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            pedidoService.cancelar(idPedido);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("pedido.canceladoOk", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/pedido/listado";
    }

    private String mensaje(String llave, Locale locale) {
        return messageSource.getMessage(llave, null, llave, locale);
    }
}
