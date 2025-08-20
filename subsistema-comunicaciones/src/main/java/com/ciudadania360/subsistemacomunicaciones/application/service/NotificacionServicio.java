package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.NotificacionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NotificacionServicio {
    private final NotificacionHandler handler;
    public NotificacionServicio(NotificacionHandler handler) { this.handler = handler; }
    public List<Notificacion> list() { return handler.list(); }
    public Notificacion get(UUID id) { return handler.get(id); }
    public Notificacion create(Notificacion e) { return handler.create(e); }
    public Notificacion update(UUID id, Notificacion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
