package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.math.BigDecimal;

@Entity
@Table(name = "notas")
@Audited
public class Nota extends AuditableEntity {
    @ManyToOne(optional = false) @JoinColumn(name = "alumno_id") private Alumno alumno;
    @ManyToOne(optional = false) @JoinColumn(name = "materia_id") private Materia materia;
    @Column(nullable = false, precision = 4, scale = 2) private BigDecimal valor;
    @Column(length = 255) private String observacion;

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
