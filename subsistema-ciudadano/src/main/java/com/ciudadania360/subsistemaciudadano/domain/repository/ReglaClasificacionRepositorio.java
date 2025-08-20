package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import java.util.UUID;

public interface ReglaClasificacionRepositorio extends JpaRepository<ReglaClasificacion, UUID> {
}
