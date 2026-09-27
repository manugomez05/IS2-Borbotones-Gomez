package com.gomez.service;

import com.gomez.entity.Usuario;
import com.gomez.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioService usuarioService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);
        usuario = new Usuario();
        usuario.setCorreoPersonal("ana@correo.com");
        usuario.setIntentosFallidos(2);
        usuario.setBloqueado(false);
        when(usuarioRepository.findByCorreoPersonal("ana@correo.com")).thenReturn(Optional.of(usuario));
    }

    @Test
    void bloqueaLaCuentaAlTercerFallo() {
        usuarioService.registrarFallo("ana@correo.com");

        assertThat(usuario.getIntentosFallidos()).isEqualTo(3);
        assertThat(usuario.isBloqueado()).isTrue();
    }
}
