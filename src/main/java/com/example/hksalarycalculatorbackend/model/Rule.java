package com.example.hksalarycalculatorbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "rules")

public class Rule {
    @Id
    @GeneratedValue
    private UUID ruleId;

    @Column(nullable = false, name = "rule_name")
    private String ruleName;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "rule_content")
    private String ruleContent;

    @Column(nullable = false, name = "status")
    private Boolean status = true;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @Column(nullable = false, name = "version")
    private Integer version;

}
