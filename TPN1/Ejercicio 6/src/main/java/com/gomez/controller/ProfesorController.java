package com.gomez.controller;

import com.gomez.dto.CambioClaveDto;
import com.gomez.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfesorController {
    private final ProfesorService profesorService;
    public ProfesorController(ProfesorService profesorService) { this.profesorService = profesorService; }

    @GetMapping("/clave")
    public String clave(Model model) {
        model.addAttribute("cambio", new CambioClaveDto());
        return "clave";
    }

    @PostMapping("/clave")
    public String cambiarClave(@Valid @ModelAttribute("cambio") CambioClaveDto dto,
                               BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) return "clave";
        try {
            profesorService.cambiarClave(authentication.getName(), dto);
            model.addAttribute("mensaje", "La clave fue actualizada correctamente.");
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "clave";
    }
}
