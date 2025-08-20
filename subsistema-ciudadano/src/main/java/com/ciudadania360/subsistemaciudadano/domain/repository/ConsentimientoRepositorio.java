package com.ciudadania360.subsistemaciudadano.domain.repository;

import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConsentimientoRepositorio extends JpaRepository<Consentimiento, UUID> {}
