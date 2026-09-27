package com.gomez.controller;

import com.gomez.dto.CambioClaveDto;
import com.gomez.entity.Usuario;
import com.gomez.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/inicio")
    public String inicio(Authentication authentication, Model model) {
        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        model.addAttribute("usuario", usuario);
        return "inicio";
    }

    @GetMapping("/clave")
    public String formularioClave(Model model) {
        model.addAttribute("cambioClave", new CambioClaveDto());
        return "clave";
    }

    @PostMapping("/clave")
    public String cambiarClave(@Valid @ModelAttribute("cambioClave") CambioClaveDto cambioClave,
                               BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            return "clave";
        }
        try {
            usuarioService.cambiarClave(authentication.getName(), cambioClave);
            model.addAttribute("mensaje", "La clave fue actualizada correctamente.");
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "clave";
    }
}
