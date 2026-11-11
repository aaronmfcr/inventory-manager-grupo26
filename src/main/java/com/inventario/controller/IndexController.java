package com.inventario.controller;

import com.inventario.service.PedidoService;
import com.inventario.service.ProductoService;
import com.inventario.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* Página de inicio (dashboard con tarjetas - Semana 7) */
@Controller
public class IndexController {

    private final ProductoService productoService;
    private final VentaService ventaService;
    private final PedidoService pedidoService;

    public IndexController(ProductoService productoService, VentaService ventaService,
            PedidoService pedidoService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalProductos", productoService.getTotalProductos());
        model.addAttribute("stockBajo", productoService.getCantidadStockBajo());
        model.addAttribute("totalPedidos", pedidoService.getTotalPedidos());
        model.addAttribute("pedidosEnProceso", pedidoService.getPedidosEnProceso());
        model.addAttribute("totalIngresos", ventaService.getTotalIngresos());
        model.addAttribute("totalVentas", ventaService.getTotalVentas());
        model.addAttribute("ultimasVentas", ventaService.getVentas().stream().limit(5).toList());
        return "index";
    }
}
