package com.example.hksalarycalculatorbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "rules")
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rulename", nullable = false)
    private String ruleName;

    @Column(name = "conditions", columnDefinition = "TEXT")
    private String conditions;

    @Column(name = "actions", columnDefinition = "TEXT")
    private String actions;

    @Column(name = "status", nullable = false)
    private boolean status = true;
}
