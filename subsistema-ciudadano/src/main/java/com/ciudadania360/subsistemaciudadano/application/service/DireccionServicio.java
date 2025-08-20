package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.DireccionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DireccionServicio {
    private final DireccionHandler handler;
    public DireccionServicio(DireccionHandler handler) { this.handler = handler; }
    public List<Direccion> list() { return handler.list(); }
    public Direccion get(UUID id) { return handler.get(id); }
    public Direccion create(Direccion e) { return handler.create(e); }
    public Direccion update(UUID id, Direccion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
