package com.gomez.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RegistroUsuarioDto {

    @NotBlank @Size(max = 60)
    private String nombre;
    @NotBlank @Size(max = 60)
    private String apellido;
    @NotBlank @Size(max = 30)
    private String documento;
    @NotNull @Past
    private LocalDate fechaNacimiento;
    @NotBlank @Email @Size(max = 120)
    private String correoPersonal;
    @NotBlank @Size(min = 8, max = 72)
    private String clave;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreoPersonal() { return correoPersonal; }
    public void setCorreoPersonal(String correoPersonal) { this.correoPersonal = correoPersonal; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
