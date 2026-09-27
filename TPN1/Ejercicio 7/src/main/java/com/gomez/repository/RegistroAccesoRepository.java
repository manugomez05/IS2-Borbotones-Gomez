package com.gomez.repository;

import com.gomez.entity.RegistroAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroAccesoRepository extends JpaRepository<RegistroAcceso, Long> {
    List<RegistroAcceso> findTop20ByOrderByIngresoDesc();
}
