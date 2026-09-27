package com.gomez.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomez.entity.GrupoFamiliar;
import com.gomez.entity.Socio;
import com.gomez.entity.Usuario;
import com.gomez.repository.GrupoFamiliarRepository;
import com.gomez.repository.SocioRepository;
import com.gomez.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner cargarDatos(UsuarioRepository usuarios, GrupoFamiliarRepository grupos,
                                  SocioRepository socios, PasswordEncoder encoder) {
        return args -> {
            if (usuarios.count() == 0) {
                Usuario usuario = new Usuario(); usuario.setCorreo("club@gomez.com");
                usuario.setClave(encoder.encode("Gomez123")); usuarios.save(usuario);
            }
            if (grupos.count() == 0) {
                GrupoFamiliar grupo = new GrupoFamiliar(); grupo.setNombre("Familia Gómez");
                grupo.setCuotaMensual(new BigDecimal("22000.00")); grupos.save(grupo);
                Socio titular = new Socio(); titular.setNombre("Martín"); titular.setApellido("Gómez");
                titular.setDocumento("40111222"); titular.setFechaNacimiento(LocalDate.of(1995, 8, 12));
                titular.setGrupoFamiliar(grupo); socios.save(titular);
            }
        };
    }
}
