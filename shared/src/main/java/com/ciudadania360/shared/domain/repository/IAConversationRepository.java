package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IAConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAConversationRepository extends JpaRepository<IAConversation, UUID> {
    Optional<IAConversation> findByConversationId(UUID conversationId);
}

