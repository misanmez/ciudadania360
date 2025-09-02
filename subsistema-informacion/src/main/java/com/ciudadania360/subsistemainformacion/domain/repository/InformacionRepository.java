package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InformacionRepository extends JpaRepository<Informacion, UUID> {}
