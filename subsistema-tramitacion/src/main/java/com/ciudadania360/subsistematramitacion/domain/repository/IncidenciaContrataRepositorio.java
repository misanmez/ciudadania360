package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IncidenciaContrataRepositorio extends JpaRepository<IncidenciaContrata, UUID> {}
