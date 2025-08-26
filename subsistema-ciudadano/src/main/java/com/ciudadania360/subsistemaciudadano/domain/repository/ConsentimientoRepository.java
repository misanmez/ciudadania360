package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConsentimientoRepository extends JpaRepository<Consentimiento, UUID> {}
