package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InstruccionRepository extends JpaRepository<Instruccion, UUID> {}
