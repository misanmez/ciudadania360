package com.ciudadania360.subsistemavideoconferencia.domain.repository;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SesionRepositorio extends JpaRepository<Sesion, UUID> {}
