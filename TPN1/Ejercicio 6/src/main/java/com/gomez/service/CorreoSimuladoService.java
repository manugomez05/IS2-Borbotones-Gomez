package com.gomez.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CorreoSimuladoService {
    private static final Logger log = LoggerFactory.getLogger(CorreoSimuladoService.class);

    public void enviarBienvenida(String destinatario, String nombre) {
        log.info("[CORREO SIMULADO] Bienvenida enviada a {} para el docente {}", destinatario, nombre);
    }
}
