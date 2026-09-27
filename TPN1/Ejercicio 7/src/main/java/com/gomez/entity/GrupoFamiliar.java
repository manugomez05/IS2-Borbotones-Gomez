package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "grupos_familiares")
@Audited
public class GrupoFamiliar extends AuditableEntity {
    @Column(nullable = false, unique = true, length = 80) private String nombre;
    @Column(nullable = false) private BigDecimal cuotaMensual;
    @OneToMany(mappedBy = "grupoFamiliar") private List<Socio> socios = new ArrayList<>();
    @OneToMany(mappedBy = "grupoFamiliar") private List<PagoCuota> pagos = new ArrayList<>();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(BigDecimal cuotaMensual) { this.cuotaMensual = cuotaMensual; }
    public List<Socio> getSocios() { return socios; }
    public List<PagoCuota> getPagos() { return pagos; }
}
