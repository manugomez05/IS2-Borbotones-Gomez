package com.gomez.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public class SocioDto {
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @NotBlank private String documento;
    @NotNull @Past private LocalDate fechaNacimiento;
    @NotNull private Long grupoFamiliarId;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public Long getGrupoFamiliarId() { return grupoFamiliarId; }
    public void setGrupoFamiliarId(Long grupoFamiliarId) { this.grupoFamiliarId = grupoFamiliarId; }
}
