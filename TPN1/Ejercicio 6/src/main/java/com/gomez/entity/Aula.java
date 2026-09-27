package com.gomez.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "aulas")
@Audited
public class Aula extends AuditableEntity {
    @Column(nullable = false, unique = true, length = 30)
    private String nombre;
    @Column(nullable = false)
    private int capacidad;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
}
