package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IntegracionRepository extends JpaRepository<Integracion, UUID> {}
