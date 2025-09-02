package com.ciudadania360.shared.domain.repository;


import com.ciudadania360.shared.domain.entity.IaChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IaChatMessageRepository extends JpaRepository<IaChatMessage, Long> {}
