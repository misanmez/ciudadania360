package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SolicitudAgrupadaRepository extends JpaRepository<SolicitudAgrupada, UUID> {
}
