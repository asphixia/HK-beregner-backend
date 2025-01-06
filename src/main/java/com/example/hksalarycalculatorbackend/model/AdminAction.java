package com.example.hksalarycalculatorbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "admin_action")

public class AdminAction {

    @Id
    @GeneratedValue
    private UUID adminActionId;

    @Column(name = "username", nullable = false)
    @NotNull
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "actionType")
    @NotNull
    private AdminActionType adminActionType;

    @Column(nullable = false, name = "ruleName")
    private String RuleName;

    @CreationTimestamp
    @Column(nullable = false, name = "timestamp")
    private LocalDateTime timestamp;


}
