package com.gomez.service;

import com.gomez.dto.CambioClaveDto;
import com.gomez.dto.RegistroUsuarioDto;
import com.gomez.entity.Usuario;
import com.gomez.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    private static final int MAXIMOS_INTENTOS = 3;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrar(RegistroUsuarioDto dto) {
        if (usuarioRepository.existsByCorreoPersonal(dto.getCorreoPersonal())) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        if (usuarioRepository.existsByDocumento(dto.getDocumento())) {
            throw new IllegalArgumentException("El documento ya está registrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setDocumento(dto.getDocumento());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setCorreoPersonal(dto.getCorreoPersonal().trim().toLowerCase());
        usuario.setClave(passwordEncoder.encode(dto.getClave()));
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscarPorCorreo(username);
        return User.withUsername(usuario.getCorreoPersonal())
                .password(usuario.getClave())
                .disabled(usuario.isBloqueado())
                .roles("USUARIO")
                .build();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoPersonal(correo.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    }

    @Transactional
    public void registrarFallo(String correo) {
        usuarioRepository.findByCorreoPersonal(correo.trim().toLowerCase()).ifPresent(usuario -> {
            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);
            if (intentos >= MAXIMOS_INTENTOS) {
                usuario.setBloqueado(true);
            }
            usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public void reiniciarFallos(String correo) {
        Usuario usuario = buscarPorCorreo(correo);
        usuario.setIntentosFallidos(0);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarClave(String correo, CambioClaveDto dto) {
        Usuario usuario = buscarPorCorreo(correo);
        if (!passwordEncoder.matches(dto.getClaveActual(), usuario.getClave())) {
            throw new IllegalArgumentException("La clave actual no es correcta.");
        }
        usuario.setClave(passwordEncoder.encode(dto.getNuevaClave()));
        usuarioRepository.save(usuario);
    }
}
