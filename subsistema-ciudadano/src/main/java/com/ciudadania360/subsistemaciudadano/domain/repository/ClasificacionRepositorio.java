package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import java.util.UUID;

public interface ClasificacionRepositorio extends JpaRepository<Clasificacion, UUID> {
}
