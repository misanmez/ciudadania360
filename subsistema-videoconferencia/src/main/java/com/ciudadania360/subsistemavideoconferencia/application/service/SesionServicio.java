package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.SesionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SesionServicio {
    private final SesionHandler handler;
    public SesionServicio(SesionHandler handler) { this.handler = handler; }
    public List<Sesion> list() { return handler.list(); }
    public Sesion get(UUID id) { return handler.get(id); }
    public Sesion create(Sesion e) { return handler.create(e); }
    public Sesion update(UUID id, Sesion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
