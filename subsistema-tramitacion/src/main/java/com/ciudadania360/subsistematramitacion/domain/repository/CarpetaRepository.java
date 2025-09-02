package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CarpetaRepository extends JpaRepository<Carpeta, UUID> {}
