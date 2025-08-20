package com.ciudadania360.subsistemavideoconferencia.domain.repository;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DispositivoRepositorio extends JpaRepository<Dispositivo, UUID> {}
