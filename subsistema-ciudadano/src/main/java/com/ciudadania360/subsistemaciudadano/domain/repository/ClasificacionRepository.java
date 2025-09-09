package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, UUID> {

    // Buscar por código exacto
    Optional<Clasificacion> findByCodigo(String codigo);

    // Buscar por nombre ignorando mayúsculas/minúsculas
    Optional<Clasificacion> findByNombreIgnoreCase(String nombre);

    // Verificar existencia de código (útil para validaciones de negocio)
    boolean existsByCodigo(String codigo);

    // Opcional: verificar existencia por nombre ignorando mayúsculas/minúsculas
    boolean existsByNombreIgnoreCase(String nombre);
}
