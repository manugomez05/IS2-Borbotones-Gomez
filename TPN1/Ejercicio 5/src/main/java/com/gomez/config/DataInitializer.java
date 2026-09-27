package com.gomez.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomez.entity.Producto;
import com.gomez.entity.ProveedorMayorista;
import com.gomez.entity.Usuario;
import com.gomez.repository.ProductoRepository;
import com.gomez.repository.ProveedorRepository;
import com.gomez.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner cargarDatos(UsuarioRepository usuarios, ProveedorRepository proveedores,
                                  ProductoRepository productos, PasswordEncoder encoder) {
        return args -> {
            if (usuarios.count() == 0) {
                Usuario usuario = new Usuario();
                usuario.setNombre("María");
                usuario.setApellido("Gómez");
                usuario.setCorreo("stock@gomez.com");
                usuario.setClave(encoder.encode("Gomez123"));
                usuarios.save(usuario);
            }
            if (proveedores.count() == 0) {
                ProveedorMayorista proveedor = new ProveedorMayorista();
                proveedor.setRazonSocial("Gomez Supply Co.");
                proveedor.setCuit("30-76543210-9");
                proveedor.setCorreo("logistica@gomezsupply.com");
                proveedor.setTelefono("2615552233");
                proveedores.save(proveedor);
            }
            if (productos.count() == 0) {
                Producto notebook = new Producto();
                notebook.setNombre("Laptop Gomez Pro 14");
                notebook.setCodigo("GZL-014");
                notebook.setPrecio(new BigDecimal("920000.00"));
                notebook.setStock(7);
                productos.save(notebook);
                Producto teclado = new Producto();
                teclado.setNombre("Teclado silenciador compact");
                teclado.setCodigo("GZK-132");
                teclado.setPrecio(new BigDecimal("72000.00"));
                teclado.setStock(18);
                productos.save(teclado);
            }
        };
    }
}
