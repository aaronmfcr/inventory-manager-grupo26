package com.inventario.controller;

import com.inventario.domain.Categoria;
import com.inventario.service.CategoriaService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU5: Categorías (se administran desde la pantalla de productos) */
@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    public CategoriaController(CategoriaService categoriaService, MessageSource messageSource) {
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    @PostMapping("/guardar")
    public String guardar(Categoria categoria, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            categoriaService.save(categoria);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("mensaje.actualizado", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Integer idCategoria, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            categoriaService.delete(idCategoria);
            redirectAttributes.addFlashAttribute("todoOk", mensaje("mensaje.eliminado", locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", mensaje(e.getMessage(), locale));
        }
        return "redirect:/producto/listado";
    }

    private String mensaje(String llave, Locale locale) {
        return messageSource.getMessage(llave, null, llave, locale);
    }
}
