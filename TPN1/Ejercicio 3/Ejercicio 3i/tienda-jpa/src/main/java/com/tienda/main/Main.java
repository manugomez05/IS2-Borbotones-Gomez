package com.tienda.main;

import com.tienda.controller.CategoriaController;
import com.tienda.controller.JPAController;
import com.tienda.controller.ProductoController;
import com.tienda.entity.Categoria;
import com.tienda.entity.Producto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        try (JPAController jpaController = new JPAController()) {
            CategoriaController categorias = new CategoriaController(jpaController);
            ProductoController productos = new ProductoController(jpaController);
            ejecutarMenu(categorias, productos);
        } catch (Exception exception) {
            System.err.println("No se pudo iniciar la aplicacion: " + exception.getMessage());
        } finally {
            SCANNER.close();
        }
    }

    private static void ejecutarMenu(CategoriaController categorias, ProductoController productos) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opcion: ");
            try {
                switch (opcion) {
                    case 1 -> crearCategoria(categorias);
                    case 2 -> mostrarLista(categorias.listar());
                    case 3 -> buscarCategoria(categorias);
                    case 4 -> modificarCategoria(categorias);
                    case 5 -> eliminarCategoria(categorias);
                    case 6 -> crearProducto(categorias, productos);
                    case 7 -> mostrarLista(productos.listar());
                    case 8 -> buscarProducto(productos);
                    case 9 -> modificarProducto(categorias, productos);
                    case 10 -> eliminarProducto(productos);
                    case 11 -> buscarPorCategoria(productos);
                    case 12 -> buscarPorNombre(productos);
                    case 13 -> mostrarLista(productos.listarOrdenadosPorPrecio());
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (RuntimeException exception) {
                System.err.println("Operacion no realizada: " + exception.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== TIENDA JPA =====");
        System.out.println("1. Crear categoria");
        System.out.println("2. Listar categorias");
        System.out.println("3. Buscar categoria");
        System.out.println("4. Modificar categoria");
        System.out.println("5. Eliminar categoria");
        System.out.println("6. Crear producto");
        System.out.println("7. Listar productos");
        System.out.println("8. Buscar producto");
        System.out.println("9. Modificar producto");
        System.out.println("10. Eliminar producto");
        System.out.println("11. Buscar productos por categoria");
        System.out.println("12. Buscar productos por nombre");
        System.out.println("13. Listar productos ordenados por precio");
        System.out.println("0. Salir");
    }

    private static void crearCategoria(CategoriaController controller) {
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripcion: ");
        controller.crear(new Categoria(nombre, descripcion));
        System.out.println("Categoria creada.");
    }

    private static void buscarCategoria(CategoriaController controller) {
        Categoria categoria = controller.buscar(leerLong("ID: "));
        System.out.println(categoria == null ? "Categoria inexistente." : categoria);
        if (categoria != null) {
            System.out.println("Productos: " + categoria.getProductos());
        }
    }

    private static void modificarCategoria(CategoriaController controller) {
        Categoria categoria = controller.buscar(leerLong("ID: "));
        if (categoria == null) {
            System.out.println("Categoria inexistente.");
            return;
        }
        categoria.setNombre(leerTexto("Nuevo nombre: "));
        categoria.setDescripcion(leerTexto("Nueva descripcion: "));
        controller.actualizar(categoria);
        System.out.println("Categoria modificada.");
    }

    private static void eliminarCategoria(CategoriaController controller) {
        controller.eliminar(leerLong("ID: "));
        System.out.println("Categoria eliminada.");
    }

    private static void crearProducto(CategoriaController categorias, ProductoController productos) {
        Long categoriaId = leerLong("ID de categoria: ");
        Categoria categoria = categorias.buscar(categoriaId);
        if (categoria == null) {
            System.out.println("La categoria no existe.");
            return;
        }
        Producto producto = new Producto(
                leerTexto("Nombre: "),
                leerDecimal("Precio: "),
                leerEntero("Stock: "),
                categoria
        );
        productos.crear(producto);
        System.out.println("Producto creado.");
    }

    private static void buscarProducto(ProductoController controller) {
        Producto producto = controller.buscar(leerLong("ID: "));
        System.out.println(producto == null ? "Producto inexistente." : producto);
    }

    private static void modificarProducto(CategoriaController categorias, ProductoController productos) {
        Producto producto = productos.buscar(leerLong("ID: "));
        if (producto == null) {
            System.out.println("Producto inexistente.");
            return;
        }
        producto.setNombre(leerTexto("Nuevo nombre: "));
        producto.setPrecio(leerDecimal("Nuevo precio: "));
        producto.setStock(leerEntero("Nuevo stock: "));
        Long categoriaId = leerLong("Nuevo ID de categoria: ");
        Categoria categoria = categorias.buscar(categoriaId);
        if (categoria == null) {
            System.out.println("La categoria no existe.");
            return;
        }
        producto.setCategoria(categoria);
        productos.actualizar(producto);
        System.out.println("Producto modificado.");
    }

    private static void eliminarProducto(ProductoController controller) {
        controller.eliminar(leerLong("ID: "));
        System.out.println("Producto eliminado.");
    }

    private static void buscarPorCategoria(ProductoController controller) {
        mostrarLista(controller.buscarPorCategoria(leerLong("ID de categoria: ")));
    }

    private static void buscarPorNombre(ProductoController controller) {
        mostrarLista(controller.buscarPorNombre(leerTexto("Nombre: ")));
    }

    private static void mostrarLista(List<?> elementos) {
        if (elementos.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        elementos.forEach(System.out::println);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = SCANNER.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El valor no puede estar vacio.");
        }
        return valor;
    }

    private static int leerEntero(String mensaje) {
        return Integer.parseInt(leerTexto(mensaje));
    }

    private static long leerLong(String mensaje) {
        return Long.parseLong(leerTexto(mensaje));
    }

    private static BigDecimal leerDecimal(String mensaje) {
        return new BigDecimal(leerTexto(mensaje).replace(',', '.'));
    }
}
