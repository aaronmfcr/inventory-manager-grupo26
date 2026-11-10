package com.inventario.controller;

import com.inventario.domain.Configuracion;
import com.inventario.service.ConfiguracionService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU12: Configuración de la información del negocio */
@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;
    private final MessageSource messageSource;

    public ConfiguracionController(ConfiguracionService configuracionService, MessageSource messageSource) {
        this.configuracionService = configuracionService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String formulario(Model model) {
        model.addAttribute("configuracion", configuracionService.getConfiguracion());
        return "configuracion/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(Configuracion configuracion, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            configuracionService.save(configuracion);
            redirectAttributes.addFlashAttribute("todoOk",
                    messageSource.getMessage("mensaje.actualizado", null, locale));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage(e.getMessage(), null, e.getMessage(), locale));
        }
        return "redirect:/configuracion";
    }
}
