package org.example.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class })
public abstract class EntityBase implements Serializable {

    @CreatedBy
    private Integer createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private Integer updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private boolean isDeleted = false;
}
