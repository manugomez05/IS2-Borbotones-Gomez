package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "alumnos")
@Audited
public class Alumno extends AuditableEntity {
    @Column(nullable = false, length = 60) private String nombre;
    @Column(nullable = false, length = 60) private String apellido;
    @Column(nullable = false, unique = true, length = 30) private String documento;
    @ManyToOne(optional = false) @JoinColumn(name = "grado_id") private Grado grado;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }
}
