package com.ciudadania360.subsistemacomunicaciones.domain.repository;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RespuestaEncuestaRepositorio extends JpaRepository<RespuestaEncuesta, UUID> {}
