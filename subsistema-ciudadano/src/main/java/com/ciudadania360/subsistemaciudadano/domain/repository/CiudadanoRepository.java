package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CiudadanoRepository extends JpaRepository<Ciudadano, UUID> {}
