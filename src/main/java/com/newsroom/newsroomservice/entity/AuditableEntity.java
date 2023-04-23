package com.newsroom.newsroomservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;


import java.time.Instant;

@Data
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    @Column(name = "deleted_at")
    protected Instant deletedAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
