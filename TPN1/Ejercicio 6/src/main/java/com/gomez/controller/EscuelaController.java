package com.gomez.controller;

import com.gomez.dto.AlumnoDto;
import com.gomez.service.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EscuelaController {
    private final AlumnoService alumnoService;
    public EscuelaController(AlumnoService alumnoService) { this.alumnoService = alumnoService; }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("alumnos", alumnoService.listar());
        return "inicio";
    }

    @GetMapping("/alumnos/nuevo")
    public String nuevoAlumno(Model model) {
        model.addAttribute("alumno", new AlumnoDto());
        return "alumno-form";
    }

    @PostMapping("/alumnos")
    public String registrarAlumno(@Valid @ModelAttribute("alumno") AlumnoDto dto,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) return "alumno-form";
        try {
            alumnoService.registrar(dto);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            return "alumno-form";
        }
        return "redirect:/inicio";
    }
}
