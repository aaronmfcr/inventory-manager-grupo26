package com.inventario.controller;

import com.inventario.domain.MensajeSoporte;
import com.inventario.service.SoporteService;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* HU13 y HU15: Sección de soporte técnico */
@Controller
@RequestMapping("/soporte")
public class SoporteController {

    private final SoporteService soporteService;
    private final MessageSource messageSource;

    public SoporteController(SoporteService soporteService, MessageSource messageSource) {
        this.soporteService = soporteService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String formulario(Model model) {
        model.addAttribute("mensajeSoporte", new MensajeSoporte());
        return "soporte/formulario";
    }

    @PostMapping("/enviar")
    public String enviar(MensajeSoporte mensajeSoporte, RedirectAttributes redirectAttributes, Locale locale) {
        soporteService.enviar(mensajeSoporte);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("soporte.enviado", null, locale));
        return "redirect:/soporte";
    }
}
