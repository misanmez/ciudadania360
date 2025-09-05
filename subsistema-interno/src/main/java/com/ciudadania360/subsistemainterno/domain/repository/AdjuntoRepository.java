package com.ciudadania360.subsistemainterno.domain.repository;

import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdjuntoRepository extends JpaRepository<Adjunto, UUID> {

    // Buscar adjuntos por parte de trabajo
    List<Adjunto> findByParteTrabajo_Id(UUID parteTrabajoId);
}
