package com.gomez.controller;

import com.gomez.dto.RegistroProfesorDto;
import com.gomez.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final ProfesorService profesorService;
    public AuthController(ProfesorService profesorService) { this.profesorService = profesorService; }

    @GetMapping({"/", "/login"})
    public String login() { return "login"; }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("profesor", new RegistroProfesorDto());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("profesor") RegistroProfesorDto dto,
                            BindingResult result, Model model) {
        if (result.hasErrors()) return "registro";
        try {
            profesorService.registrar(dto);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            return "registro";
        }
        return "redirect:/login?registrado";
    }
}
