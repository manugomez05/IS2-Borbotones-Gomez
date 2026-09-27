package com.gomez.repository;

import com.gomez.entity.ProveedorMayorista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<ProveedorMayorista, Long> { }
