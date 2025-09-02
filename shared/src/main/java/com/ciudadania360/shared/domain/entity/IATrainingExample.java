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
public class IATrainingExample {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_message", nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "response", nullable = false, columnDefinition = "TEXT")
    private String response;

    @Column(name = "used_for_training")
    private Boolean usedForTraining = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
