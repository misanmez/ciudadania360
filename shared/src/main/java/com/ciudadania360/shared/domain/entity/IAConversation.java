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
public class IAConversation {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IAChatMessage> messages;

    @Column(name = "closed")
    private boolean closed = false;
}

