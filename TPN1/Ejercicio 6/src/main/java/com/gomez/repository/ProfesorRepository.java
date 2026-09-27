package com.gomez.repository;

import com.gomez.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
