package com.ciudadania360.shared.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "training_example", schema = "ia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IaTrainingExample {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String userMessage;

    @Column(nullable = false)
    private String response;

    @Column(nullable = false)
    private boolean usedForTraining = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
