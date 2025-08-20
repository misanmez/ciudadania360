package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.CitaVideollamadaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CitaVideollamadaServicio {
    private final CitaVideollamadaHandler handler;
    public CitaVideollamadaServicio(CitaVideollamadaHandler handler) { this.handler = handler; }
    public List<CitaVideollamada> list() { return handler.list(); }
    public CitaVideollamada get(UUID id) { return handler.get(id); }
    public CitaVideollamada create(CitaVideollamada e) { return handler.create(e); }
    public CitaVideollamada update(UUID id, CitaVideollamada e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
