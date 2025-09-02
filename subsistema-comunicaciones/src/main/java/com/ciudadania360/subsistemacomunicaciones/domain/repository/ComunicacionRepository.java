package com.ciudadania360.subsistemacomunicaciones.domain.repository;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ComunicacionRepository extends JpaRepository<Comunicacion, UUID> {}
