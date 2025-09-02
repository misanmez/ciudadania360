package com.ciudadania360.subsistemainformacion.domain.repository;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DudaRepository extends JpaRepository<Duda, UUID> {}
