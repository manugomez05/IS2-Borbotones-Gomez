package com.gomez.repository;

import com.gomez.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Long> {
    List<Socio> findAllByOrderByApellidoAscNombreAsc();
}
