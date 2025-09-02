package com.ciudadania360.gestionrolespermisos.domain.repository;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RolRepository extends JpaRepository<Rol, UUID> {}
