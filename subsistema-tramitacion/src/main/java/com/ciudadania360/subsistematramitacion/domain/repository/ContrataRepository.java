package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ContrataRepository extends JpaRepository<Contrata, UUID> {}
