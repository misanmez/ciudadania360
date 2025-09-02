package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IATrainingExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IATrainingExampleRepository extends JpaRepository<IATrainingExample, UUID> {
    List<IATrainingExample> findByUsedForTrainingFalse();

    Optional<IATrainingExample> findByUserMessageContainingIgnoreCase(String text);
}
