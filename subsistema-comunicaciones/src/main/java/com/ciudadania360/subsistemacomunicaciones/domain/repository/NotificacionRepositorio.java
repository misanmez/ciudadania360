package com.ciudadania360.subsistemacomunicaciones.domain.repository;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificacionRepositorio extends JpaRepository<Notificacion, UUID> {}
