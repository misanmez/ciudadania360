package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.RespuestaEncuestaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RespuestaEncuestaServicio {
    private final RespuestaEncuestaHandler handler;
    public RespuestaEncuestaServicio(RespuestaEncuestaHandler handler) { this.handler = handler; }
    public List<RespuestaEncuesta> list() { return handler.list(); }
    public RespuestaEncuesta get(UUID id) { return handler.get(id); }
    public RespuestaEncuesta create(RespuestaEncuesta e) { return handler.create(e); }
    public RespuestaEncuesta update(UUID id, RespuestaEncuesta e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
