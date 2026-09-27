package com.gomez.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@MappedSuperclass
@Audited
public abstract class AuditableEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    public Long getId() { return id; }
}
