package com.tienda.controller;

import com.tienda.entity.Categoria;
import com.tienda.entity.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductoController {
    private final JPAController jpaController;

    public ProductoController(JPAController jpaController) {
        this.jpaController = jpaController;
    }

    public Producto crear(Producto producto) {
        ejecutarEnTransaccion(entityManager -> entityManager.persist(producto));
        return producto;
    }

    public Producto buscar(Long id) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.find(Producto.class, id);
        }
    }

    public List<Producto> listar() {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Producto p ORDER BY p.id", Producto.class)
                    .getResultList();
        }
    }

    public List<Producto> buscarPorNombre(String nombre) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(:nombre)", Producto.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        }
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId", Producto.class)
                    .setParameter("categoriaId", categoriaId)
                    .getResultList();
        }
    }

    public List<Producto> listarOrdenadosPorPrecio() {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Producto p ORDER BY p.precio", Producto.class)
                    .getResultList();
        }
    }

    public Producto actualizar(Producto producto) {
        ejecutarEnTransaccion(entityManager -> entityManager.merge(producto));
        return producto;
    }

    public void eliminar(Long id) {
        ejecutarEnTransaccion(entityManager -> {
            Producto producto = entityManager.find(Producto.class, id);
            if (producto == null) {
                throw new IllegalArgumentException("No existe un producto con ese ID.");
            }
            entityManager.remove(producto);
        });
    }

    public Categoria buscarCategoria(Long categoriaId) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.find(Categoria.class, categoriaId);
        }
    }

    private void ejecutarEnTransaccion(OperacionJPA operacion) {
        EntityManager entityManager = jpaController.crearEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            operacion.ejecutar(entityManager);
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    @FunctionalInterface
    private interface OperacionJPA {
        void ejecutar(EntityManager entityManager);
    }
}
