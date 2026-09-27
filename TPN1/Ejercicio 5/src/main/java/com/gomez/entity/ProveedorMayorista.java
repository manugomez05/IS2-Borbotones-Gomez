package com.gomez.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proveedores_mayoristas")
public class ProveedorMayorista {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120) private String razonSocial;
    @Column(nullable = false, unique = true, length = 30) private String cuit;
    @Column(nullable = false, length = 120) private String correo;
    @Column(nullable = false, length = 30) private String telefono;
    @OneToMany(mappedBy = "proveedor") private List<OrdenCompra> ordenes = new ArrayList<>();

    public Long getId() { return id; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<OrdenCompra> getOrdenes() { return ordenes; }
}
