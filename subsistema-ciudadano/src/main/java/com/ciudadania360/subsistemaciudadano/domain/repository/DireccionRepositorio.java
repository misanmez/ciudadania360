package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DireccionRepositorio extends JpaRepository<Direccion, UUID> {}
