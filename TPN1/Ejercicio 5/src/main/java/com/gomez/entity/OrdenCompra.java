package com.gomez.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 30) private String numero;
    @Column(nullable = false) private LocalDate fecha;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EstadoOrden estado = EstadoOrden.PENDIENTE;
    @ManyToOne(optional = false) @JoinColumn(name = "proveedor_id") private ProveedorMayorista proveedor;
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles = new ArrayList<>();

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public ProveedorMayorista getProveedor() { return proveedor; }
    public void setProveedor(ProveedorMayorista proveedor) { this.proveedor = proveedor; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public void agregarDetalle(DetalleOrden detalle) { detalles.add(detalle); detalle.setOrden(this); }
}
