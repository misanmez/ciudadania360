package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProcesoBPMRepository extends JpaRepository<ProcesoBPM, UUID> {}
