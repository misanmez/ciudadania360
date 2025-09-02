package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SugerenciaRepository extends JpaRepository<Sugerencia, UUID> {}
