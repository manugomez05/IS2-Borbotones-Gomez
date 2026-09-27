package com.gomez.service;

import com.gomez.dto.CambioClaveDto;
import com.gomez.entity.Profesor;
import com.gomez.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {
    @Mock ProfesorRepository profesorRepository;
    @Mock PasswordEncoder passwordEncoder;

    @Test
    void rechazaCambioCuandoLaClaveActualEsIncorrecta() {
        Profesor profesor = new Profesor(); profesor.setCorreo("docente@correo.com"); profesor.setClave("hash");
        when(profesorRepository.findByCorreo("docente@correo.com")).thenReturn(Optional.of(profesor));
        when(passwordEncoder.matches("incorrecta", "hash")).thenReturn(false);
        ProfesorService service = new ProfesorService(profesorRepository, passwordEncoder, new CorreoSimuladoService());
        CambioClaveDto dto = new CambioClaveDto(); dto.setClaveActual("incorrecta"); dto.setNuevaClave("NuevaClave123");

        assertThatThrownBy(() -> service.cambiarClave("docente@correo.com", dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La clave actual no es correcta.");
    }
}
