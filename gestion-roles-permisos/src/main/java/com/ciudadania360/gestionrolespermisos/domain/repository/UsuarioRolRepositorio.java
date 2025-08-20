package com.ciudadania360.gestionrolespermisos.domain.repository;

import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioRolRepositorio extends JpaRepository<UsuarioRol, UUID> {}
