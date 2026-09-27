package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDate;

@Entity
@Table(name = "socios")
@Audited
public class Socio extends AuditableEntity {
    @Column(nullable = false, length = 60) private String nombre;
    @Column(nullable = false, length = 60) private String apellido;
    @Column(nullable = false, unique = true, length = 30) private String documento;
    @Column(nullable = false) private LocalDate fechaNacimiento;
    @ManyToOne(optional = false) @JoinColumn(name = "grupo_familiar_id") private GrupoFamiliar grupoFamiliar;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public GrupoFamiliar getGrupoFamiliar() { return grupoFamiliar; }
    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) { this.grupoFamiliar = grupoFamiliar; }
}
