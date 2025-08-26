package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SolicitudRepository extends JpaRepository<Solicitud, UUID>, JpaSpecificationExecutor<Solicitud> {
}
