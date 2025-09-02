package com.ciudadania360.shared.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_message", schema = "ia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IaChatMessage {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private IaConversation conversation;

    @Column(name = "user_message", nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "raw_response", columnDefinition = "JSONB")
    private String rawResponse;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}


