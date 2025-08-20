package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import java.util.UUID;

public interface SolicitudRepositorio extends JpaRepository<Solicitud, UUID> {
}
