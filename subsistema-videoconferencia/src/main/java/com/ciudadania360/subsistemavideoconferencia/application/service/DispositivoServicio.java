package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.DispositivoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DispositivoServicio {
    private final DispositivoHandler handler;
    public DispositivoServicio(DispositivoHandler handler) { this.handler = handler; }
    public List<Dispositivo> list() { return handler.list(); }
    public Dispositivo get(UUID id) { return handler.get(id); }
    public Dispositivo create(Dispositivo e) { return handler.create(e); }
    public Dispositivo update(UUID id, Dispositivo e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
