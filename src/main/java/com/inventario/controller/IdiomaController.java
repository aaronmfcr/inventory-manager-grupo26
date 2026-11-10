package com.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/* HU14: pantalla de cambio de idioma (el cambio lo hace el LocaleChangeInterceptor con ?lang=) */
@Controller
public class IdiomaController {

    @GetMapping("/idioma")
    public String idioma() {
        return "idioma";
    }
}
