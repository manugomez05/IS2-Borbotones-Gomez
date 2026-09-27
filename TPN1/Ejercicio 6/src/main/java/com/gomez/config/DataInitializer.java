package com.gomez.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomez.entity.Alumno;
import com.gomez.entity.Aula;
import com.gomez.entity.Grado;
import com.gomez.entity.Profesor;
import com.gomez.repository.AlumnoRepository;
import com.gomez.repository.AulaRepository;
import com.gomez.repository.GradoRepository;
import com.gomez.repository.ProfesorRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner cargarDatos(ProfesorRepository profesores, AulaRepository aulas,
                                  GradoRepository grados, AlumnoRepository alumnos,
                                  PasswordEncoder encoder) {
        return args -> {
            if (profesores.count() == 0) {
                Profesor profesor = new Profesor();
                profesor.setNombre("María"); profesor.setApellido("López"); profesor.setSexo("Femenino");
                profesor.setFechaNacimiento(LocalDate.of(1985, 5, 20));
                profesor.setCorreo("docente@gomez.com"); profesor.setClave(encoder.encode("Gomez123"));
                profesores.save(profesor);
            }
            if (aulas.count() == 0) {
                Aula aula = new Aula(); aula.setNombre("Aula 1"); aula.setCapacidad(30); aulas.save(aula);
                Grado grado = new Grado(); grado.setNombre("Primer grado"); grado.setAula(aula); grados.save(grado);
                Alumno alumno = new Alumno(); alumno.setNombre("Ana"); alumno.setApellido("Gómez");
                alumno.setDocumento("40111222"); alumno.setGrado(grado); alumnos.save(alumno);
            }
        };
    }
}
