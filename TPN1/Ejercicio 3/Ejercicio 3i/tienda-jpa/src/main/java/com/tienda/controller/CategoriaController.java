package com.tienda.controller;

import com.tienda.entity.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;

public class CategoriaController {
    private final JPAController jpaController;

    public CategoriaController(JPAController jpaController) {
        this.jpaController = jpaController;
    }

    public Categoria crear(Categoria categoria) {
        ejecutarEnTransaccion(entityManager -> entityManager.persist(categoria));
        return categoria;
    }

    public Categoria buscar(Long id) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.find(Categoria.class, id);
        }
    }

    public List<Categoria> listar() {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class)
                    .getResultList();
        }
    }

    public Categoria buscarPorNombre(String nombre) {
        try (EntityManager entityManager = jpaController.crearEntityManager()) {
            return entityManager.createQuery("SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public Categoria actualizar(Categoria categoria) {
        ejecutarEnTransaccion(entityManager -> entityManager.merge(categoria));
        return categoria;
    }

    public void eliminar(Long id) {
        ejecutarEnTransaccion(entityManager -> {
            Categoria categoria = entityManager.find(Categoria.class, id);
            if (categoria == null) {
                throw new IllegalArgumentException("No existe una categoria con ese ID.");
            }
            if (!categoria.getProductos().isEmpty()) {
                throw new IllegalStateException("No se puede eliminar una categoria con productos asociados.");
            }
            entityManager.remove(categoria);
        });
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
