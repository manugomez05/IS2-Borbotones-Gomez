package com.gomez.service;

import com.gomez.dto.ProductoDto;
import com.gomez.repository.OrdenCompraRepository;
import com.gomez.repository.ProductoRepository;
import com.gomez.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {
    @Mock ProductoRepository productoRepository;
    @Mock ProveedorRepository proveedorRepository;
    @Mock OrdenCompraRepository ordenCompraRepository;

    @Test
    void guardaUnProductoConLosDatosDelDto() {
        CompraService service = new CompraService(productoRepository, proveedorRepository, ordenCompraRepository);
        ProductoDto dto = new ProductoDto();
        dto.setNombre("Monitor"); dto.setCodigo("MON-01"); dto.setPrecio(new BigDecimal("100.00")); dto.setStock(4);
        service.crearProducto(dto);
        verify(productoRepository).save(org.mockito.ArgumentMatchers.any());
    }
}
