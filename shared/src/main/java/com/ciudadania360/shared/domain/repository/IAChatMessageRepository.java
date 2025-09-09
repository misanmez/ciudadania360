package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IAChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAChatMessageRepository extends JpaRepository<IAChatMessage, Long> {
    List<IAChatMessage> findByConversation_Id(UUID conversationId);
}
