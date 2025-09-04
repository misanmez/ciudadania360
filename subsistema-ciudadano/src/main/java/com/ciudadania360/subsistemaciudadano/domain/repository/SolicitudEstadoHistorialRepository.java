package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SolicitudEstadoHistorialRepository extends JpaRepository<SolicitudEstadoHistorial, UUID> {
}
