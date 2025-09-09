package com.ciudadania360.subsistemaciudadano.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, UUID> {
}
