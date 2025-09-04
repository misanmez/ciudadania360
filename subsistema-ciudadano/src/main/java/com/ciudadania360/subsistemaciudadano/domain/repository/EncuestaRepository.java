package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EncuestaRepository extends JpaRepository<Encuesta, UUID> {
}
