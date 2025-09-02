package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IaConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IaConversationRepository extends JpaRepository<IaConversation, UUID> {
    Optional<IaConversation> findByConversationId(UUID conversationId);
}

