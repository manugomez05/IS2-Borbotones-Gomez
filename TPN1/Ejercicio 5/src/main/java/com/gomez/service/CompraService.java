package com.gomez.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomez.dto.OrdenCompraDto;
import com.gomez.dto.ProductoDto;
import com.gomez.entity.DetalleOrden;
import com.gomez.entity.OrdenCompra;
import com.gomez.entity.Producto;
import com.gomez.entity.ProveedorMayorista;
import com.gomez.repository.OrdenCompraRepository;
import com.gomez.repository.ProductoRepository;
import com.gomez.repository.ProveedorRepository;

@Service
public class CompraService {
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final OrdenCompraRepository ordenCompraRepository;

    public CompraService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository,
                         OrdenCompraRepository ordenCompraRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> productos() { return productoRepository.findByActivoTrueOrderByNombreAsc(); }

    @Transactional(readOnly = true)
    public List<ProveedorMayorista> proveedores() { return proveedorRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<OrdenCompra> ordenes() { return ordenCompraRepository.findAllByOrderByFechaDesc(); }

    @Transactional
    public void crearProducto(ProductoDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (dto.getCodigo() == null || dto.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }
        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        if (dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre().trim());
        producto.setCodigo(dto.getCodigo().trim().toUpperCase());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        productoRepository.save(producto);
    }

    @Transactional
    public void crearOrden(OrdenCompraDto dto) {
        if (dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        ProveedorMayorista proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor inexistente."));
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto inexistente."));
        OrdenCompra orden = new OrdenCompra();
        orden.setNumero("OC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        orden.setFecha(LocalDate.now());
        orden.setProveedor(proveedor);
        DetalleOrden detalle = new DetalleOrden();
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        orden.agregarDetalle(detalle);
        ordenCompraRepository.save(orden);
    }
}
