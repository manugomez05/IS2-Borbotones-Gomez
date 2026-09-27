package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDate;

@Entity
@Table(name = "profesores")
@Audited
public class Profesor extends AuditableEntity {
    @Column(nullable = false, length = 60) private String nombre;
    @Column(nullable = false, length = 60) private String apellido;
    @Column(nullable = false, length = 20) private String sexo;
    @Column(nullable = false) private LocalDate fechaNacimiento;
    @Column(nullable = false, unique = true, length = 120) private String correo;
    @Column(nullable = false) private String clave;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
