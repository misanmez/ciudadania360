package com.ciudadania360.gestionrolespermisos.domain.repository;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PermisoRepositorio extends JpaRepository<Permiso, UUID> {}
