package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RecomendacionRepositorio extends JpaRepository<Recomendacion, UUID> {}
