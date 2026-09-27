package com.gomez.repository;

import com.gomez.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoPersonal(String correoPersonal);
    boolean existsByCorreoPersonal(String correoPersonal);
    boolean existsByDocumento(String documento);
}
