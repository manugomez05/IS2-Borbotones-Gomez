package com.tienda.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAController implements AutoCloseable {
    private final EntityManagerFactory entityManagerFactory;

    public JPAController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tiendaPU");
    }

    public EntityManager crearEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void close() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
