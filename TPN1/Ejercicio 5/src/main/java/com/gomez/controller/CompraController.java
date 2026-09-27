package com.gomez.controller;

import com.gomez.dto.OrdenCompraDto;
import com.gomez.dto.ProductoDto;
import com.gomez.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompraController {
    private final CompraService compraService;

    public CompraController(CompraService compraService) { this.compraService = compraService; }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("productos", compraService.productos());
        model.addAttribute("ordenes", compraService.ordenes());
        return "inicio";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new ProductoDto());
        return "producto-form";
    }

    @PostMapping("/productos")
    public String crearProducto(@Valid @ModelAttribute("producto") ProductoDto dto,
                                BindingResult result) {
        if (result.hasErrors()) return "producto-form";
        compraService.crearProducto(dto);
        return "redirect:/inicio";
    }

    @GetMapping("/ordenes/nueva")
    public String nuevaOrden(Model model) {
        model.addAttribute("orden", new OrdenCompraDto());
        model.addAttribute("proveedores", compraService.proveedores());
        model.addAttribute("productos", compraService.productos());
        return "orden-form";
    }

    @PostMapping("/ordenes")
    public String crearOrden(@Valid @ModelAttribute("orden") OrdenCompraDto dto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("proveedores", compraService.proveedores());
            model.addAttribute("productos", compraService.productos());
            return "orden-form";
        }
        compraService.crearOrden(dto);
        return "redirect:/inicio";
    }
}
