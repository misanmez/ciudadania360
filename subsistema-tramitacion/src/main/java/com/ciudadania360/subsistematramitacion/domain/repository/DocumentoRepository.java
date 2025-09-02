package com.ciudadania360.subsistematramitacion.domain.repository;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DocumentoRepository extends JpaRepository<Documento, UUID> {}
