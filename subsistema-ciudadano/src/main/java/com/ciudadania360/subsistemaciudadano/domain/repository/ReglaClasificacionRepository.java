package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReglaClasificacionRepository extends JpaRepository<ReglaClasificacion, UUID> {

    /**
     * Busca una regla por nombre ignorando mayúsculas/minúsculas.
     */
    Optional<ReglaClasificacion> findByNombreIgnoreCase(String nombre);
}
