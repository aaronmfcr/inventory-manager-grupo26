package com.inventario.controller;

import com.inventario.domain.Categoria;
import com.inventario.domain.Producto;
import com.inventario.service.CategoriaService;
import com.inventario.service.ProductoService;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU1, HU2, HU3, HU4, HU5 y HU8 */
@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService,
            MessageSource messageSource) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    /* Listado con búsqueda (HU4) y filtro por categoría (HU5) */
    @GetMapping("/listado")
    public String listado(@RequestParam(required = false) String buscar,
            @RequestParam(required = false) Integer categoria, Model model) {
        List<Producto> productos;
        if (buscar != null && !buscar.isBlank()) {
            productos = productoService.buscar(buscar.trim());
            model.addAttribute("buscar", buscar.trim());
        } else if (categoria != null) {
            productos = productoService.getProductosPorCategoria(categoria);
            model.addAttribute("categoriaActual", categoria);
        } else {
            productos = productoService.getProductos();
        }
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("producto", new Producto());
        model.addAttribute("categoria", new Categoria());
        return "producto/listado";
    }

    /* HU4: pantalla de búsqueda de productos por nombre o SKU */
    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String buscar, Model model) {
        if (buscar != null && !buscar.isBlank()) {
            model.addAttribute("productos", productoService.buscar(buscar.trim()));
            model.addAttribute("buscar", buscar.trim());
        }
        return "producto/buscar";
    }

    /* HU1 (agregar) y HU2 (modificar) usan el mismo método */
    @PostMapping("/guardar")
    public String guardar(Producto producto, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("mensaje.actualizado", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/producto/listado";
    }

    /* HU3: eliminar producto (se confirma en una ventana modal) */
    @PostMapping("/eliminar")
    public String eliminar(Integer idProducto, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            productoService.delete(idProducto);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("mensaje.eliminado", locale));
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", mensaje("producto.error.eliminar", locale));
        }
        return "redirect:/producto/listado";
    }

    /* HU2: pantalla para modificar un producto */
    @GetMapping("/modificar/{idProducto}")
    public String modificar(@PathVariable Integer idProducto, Model model,
            RedirectAttributes redirectAttributes, Locale locale) {
        var producto = productoService.getProducto(idProducto);
        if (producto.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", mensaje("producto.error.noExiste", locale));
            return "redirect:/producto/listado";
        }
        model.addAttribute("producto", producto.get());
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "producto/modifica";
    }

    /* HU8: actualizar el stock cuando se reponen productos */
    @PostMapping("/reponer")
    public String reponer(Integer idProducto, Integer cantidad, RedirectAttributes redirectAttributes,
            Locale locale) {
        try {
            productoService.reponer(idProducto, cantidad);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("producto.stockActualizado", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/producto/listado";
    }

    private String mensaje(String llave, Locale locale) {
        return messageSource.getMessage(llave, null, llave, locale);
    }
}
