package com.gomez.controller;

import com.gomez.dto.*;
import com.gomez.entity.MedioPago;
import com.gomez.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClubController {
    private final ClubService service;
    public ClubController(ClubService service) { this.service = service; }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("socios", service.socios()); model.addAttribute("grupos", service.grupos());
        model.addAttribute("accesos", service.accesos()); model.addAttribute("pagos", service.pagos());
        return "inicio";
    }

    @GetMapping("/socios/nuevo")
    public String nuevoSocio(Model model) { model.addAttribute("socio", new SocioDto()); model.addAttribute("grupos", service.grupos()); return "socio-form"; }
    @PostMapping("/socios")
    public String registrarSocio(@Valid @ModelAttribute("socio") SocioDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) { model.addAttribute("grupos", service.grupos()); return "socio-form"; }
        service.registrarSocio(dto); return "redirect:/inicio";
    }

    @GetMapping("/pagos/nuevo")
    public String nuevoPago(Model model) { model.addAttribute("pago", new PagoCuotaDto()); model.addAttribute("grupos", service.grupos()); model.addAttribute("medios", MedioPago.values()); return "pago-form"; }
    @PostMapping("/pagos")
    public String registrarPago(@Valid @ModelAttribute("pago") PagoCuotaDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) { model.addAttribute("grupos", service.grupos()); model.addAttribute("medios", MedioPago.values()); return "pago-form"; }
        service.registrarPago(dto); return "redirect:/inicio";
    }

    @GetMapping("/accesos/nuevo")
    public String nuevoAcceso(Model model) { model.addAttribute("acceso", new AccesoDto()); model.addAttribute("socios", service.socios()); return "acceso-form"; }
    @PostMapping("/accesos")
    public String registrarIngreso(@Valid @ModelAttribute("acceso") AccesoDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) { model.addAttribute("socios", service.socios()); return "acceso-form"; }
        service.registrarIngreso(dto); return "redirect:/inicio";
    }
    @PostMapping("/accesos/{id}/egreso")
    public String registrarEgreso(@PathVariable Long id) { service.registrarEgreso(id); return "redirect:/inicio"; }
}
