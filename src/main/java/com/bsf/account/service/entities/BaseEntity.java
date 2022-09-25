package com.bsf.account.service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_ts")
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_ts")
    private LocalDateTime updatedTimestamp;
}
