package com.ciudadania360.subsistemacomunicaciones.domain.repository;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EncuestaRepositorio extends JpaRepository<Encuesta, UUID> {}
