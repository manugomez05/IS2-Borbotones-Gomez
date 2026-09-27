package com.gomez.service;

import com.gomez.dto.CambioClaveDto;
import com.gomez.dto.RegistroProfesorDto;
import com.gomez.entity.Profesor;
import com.gomez.repository.ProfesorRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesorService implements UserDetailsService {
    private final ProfesorRepository profesorRepository;
    private final PasswordEncoder passwordEncoder;
    private final CorreoSimuladoService correoSimuladoService;

    public ProfesorService(ProfesorRepository profesorRepository, PasswordEncoder passwordEncoder,
                           CorreoSimuladoService correoSimuladoService) {
        this.profesorRepository = profesorRepository;
        this.passwordEncoder = passwordEncoder;
        this.correoSimuladoService = correoSimuladoService;
    }

    @Transactional
    public void registrar(RegistroProfesorDto dto) {
        String correo = dto.getCorreo().trim().toLowerCase();
        if (profesorRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.getNombre());
        profesor.setApellido(dto.getApellido());
        profesor.setSexo(dto.getSexo());
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setCorreo(correo);
        profesor.setClave(passwordEncoder.encode(dto.getClave()));
        profesorRepository.save(profesor);
        correoSimuladoService.enviarBienvenida(correo, profesor.getNombre());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profesor profesor = buscarPorCorreo(username);
        return User.withUsername(profesor.getCorreo()).password(profesor.getClave()).roles("DOCENTE").build();
    }

    @Transactional(readOnly = true)
    public Profesor buscarPorCorreo(String correo) {
        return profesorRepository.findByCorreo(correo.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Docente no encontrado."));
    }

    @Transactional
    public void cambiarClave(String correo, CambioClaveDto dto) {
        Profesor profesor = buscarPorCorreo(correo);
        if (!passwordEncoder.matches(dto.getClaveActual(), profesor.getClave())) {
            throw new IllegalArgumentException("La clave actual no es correcta.");
        }
        profesor.setClave(passwordEncoder.encode(dto.getNuevaClave()));
        profesorRepository.save(profesor);
    }
}
