package com.gomez.repository;

import com.gomez.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    List<Alumno> findAllByOrderByApellidoAscNombreAsc();
}
