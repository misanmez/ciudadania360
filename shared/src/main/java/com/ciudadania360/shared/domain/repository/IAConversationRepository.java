package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IAConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAConversationRepository extends JpaRepository<IAConversation, UUID> {
    // El método findById ya está heredado de JpaRepository
}

