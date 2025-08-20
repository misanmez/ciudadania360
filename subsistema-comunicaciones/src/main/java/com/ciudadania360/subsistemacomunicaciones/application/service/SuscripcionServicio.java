package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.SuscripcionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SuscripcionServicio {
    private final SuscripcionHandler handler;
    public SuscripcionServicio(SuscripcionHandler handler) { this.handler = handler; }
    public List<Suscripcion> list() { return handler.list(); }
    public Suscripcion get(UUID id) { return handler.get(id); }
    public Suscripcion create(Suscripcion e) { return handler.create(e); }
    public Suscripcion update(UUID id, Suscripcion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
