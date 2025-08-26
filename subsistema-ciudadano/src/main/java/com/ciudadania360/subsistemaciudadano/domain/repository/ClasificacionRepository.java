package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;

import java.util.Optional;
import java.util.UUID;

public interface ClasificacionRepository extends JpaRepository<Clasificacion, UUID> {
    Optional<Clasificacion> findByCodigo(String codigo);

    Optional<Clasificacion> findByNombreIgnoreCase(String nombre);
}
