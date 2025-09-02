package com.ciudadania360.shared.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conversation", schema = "ia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IaConversation {

    @Id
    @GeneratedValue
    @Column(name = "conversation_id")
    private UUID conversationId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IaChatMessage> messages;
}

