package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PasoRepository extends JpaRepository<Paso, UUID> {}
