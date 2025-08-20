package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import java.util.UUID;

public interface InteraccionRepositorio extends JpaRepository<Interaccion, UUID> {
}
