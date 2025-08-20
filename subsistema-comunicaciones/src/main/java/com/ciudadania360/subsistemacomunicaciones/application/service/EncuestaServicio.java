package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.EncuestaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EncuestaServicio {
    private final EncuestaHandler handler;
    public EncuestaServicio(EncuestaHandler handler) { this.handler = handler; }
    public List<Encuesta> list() { return handler.list(); }
    public Encuesta get(UUID id) { return handler.get(id); }
    public Encuesta create(Encuesta e) { return handler.create(e); }
    public Encuesta update(UUID id, Encuesta e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
