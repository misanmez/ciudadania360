package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IndicadorRepositorio extends JpaRepository<Indicador, UUID> {}
