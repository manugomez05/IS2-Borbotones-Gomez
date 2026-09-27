package com.gomez.controller;

import com.gomez.dto.RegistroUsuarioDto;
import com.gomez.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("registro", new RegistroUsuarioDto());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("registro") RegistroUsuarioDto registro,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registro";
        }
        try {
            usuarioService.registrar(registro);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            return "registro";
        }
        return "redirect:/login?registrado";
    }
}
