package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import java.util.UUID;

public interface UbicacionRepositorio extends JpaRepository<Ubicacion, UUID> {
}
