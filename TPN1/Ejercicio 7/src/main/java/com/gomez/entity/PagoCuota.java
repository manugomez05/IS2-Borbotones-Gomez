package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagos_cuota")
@Audited
public class PagoCuota extends AuditableEntity {
    @ManyToOne(optional = false) @JoinColumn(name = "grupo_familiar_id") private GrupoFamiliar grupoFamiliar;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal importe;
    @Column(nullable = false) private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private MedioPago medioPago;

    public GrupoFamiliar getGrupoFamiliar() { return grupoFamiliar; }
    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) { this.grupoFamiliar = grupoFamiliar; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public MedioPago getMedioPago() { return medioPago; }
    public void setMedioPago(MedioPago medioPago) { this.medioPago = medioPago; }
}
