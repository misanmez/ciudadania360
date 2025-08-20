package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.ComunicacionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ComunicacionServicio {
    private final ComunicacionHandler handler;
    public ComunicacionServicio(ComunicacionHandler handler) { this.handler = handler; }
    public List<Comunicacion> list() { return handler.list(); }
    public Comunicacion get(UUID id) { return handler.get(id); }
    public Comunicacion create(Comunicacion e) { return handler.create(e); }
    public Comunicacion update(UUID id, Comunicacion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
