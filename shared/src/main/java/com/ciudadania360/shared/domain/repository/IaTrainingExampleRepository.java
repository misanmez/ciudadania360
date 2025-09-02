package com.ciudadania360.shared.domain.repository;

import com.ciudadania360.shared.domain.entity.IaTrainingExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IaTrainingExampleRepository extends JpaRepository<IaTrainingExample, UUID> {
    List<IaTrainingExample> findByUsedForTrainingFalse();
}
