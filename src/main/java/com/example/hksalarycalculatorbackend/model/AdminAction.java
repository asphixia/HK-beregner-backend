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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "actionType")
    @NotNull
    private AdminActionType adminActionType;

    @CreationTimestamp
    @Column(nullable = false, name = "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    @NotNull
    private Status status;
}
