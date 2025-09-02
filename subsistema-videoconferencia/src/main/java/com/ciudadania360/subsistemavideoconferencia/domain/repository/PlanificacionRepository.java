package com.ciudadania360.subsistemavideoconferencia.domain.repository;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlanificacionRepository extends JpaRepository<Planificacion, UUID> {}
