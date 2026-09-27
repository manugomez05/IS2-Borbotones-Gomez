package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "grados")
@Audited
public class Grado extends AuditableEntity {
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;
    @ManyToOne(optional = false)
    @JoinColumn(name = "aula_id")
    private Aula aula;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }
}
