package com.ciudadania360.subsistemavideoconferencia.domain.repository;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CitaVideollamadaRepository extends JpaRepository<CitaVideollamada, UUID> {}
