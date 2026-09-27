package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_acceso")
@Audited
public class RegistroAcceso extends AuditableEntity {
    @ManyToOne(optional = false) @JoinColumn(name = "socio_id") private Socio socio;
    @Column(nullable = false) private LocalDateTime ingreso;
    private LocalDateTime egreso;
    @Lob @Column(name = "imagen_rostro") private byte[] imagenRostro;

    public Socio getSocio() { return socio; }
    public void setSocio(Socio socio) { this.socio = socio; }
    public LocalDateTime getIngreso() { return ingreso; }
    public void setIngreso(LocalDateTime ingreso) { this.ingreso = ingreso; }
    public LocalDateTime getEgreso() { return egreso; }
    public void setEgreso(LocalDateTime egreso) { this.egreso = egreso; }
    public byte[] getImagenRostro() { return imagenRostro; }
    public void setImagenRostro(byte[] imagenRostro) { this.imagenRostro = imagenRostro; }
}
