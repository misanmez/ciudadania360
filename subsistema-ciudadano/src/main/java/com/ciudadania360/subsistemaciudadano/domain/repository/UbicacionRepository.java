package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import java.util.UUID;

public interface UbicacionRepository extends JpaRepository<Ubicacion, UUID> {
}
