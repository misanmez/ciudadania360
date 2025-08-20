package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CiudadanoServicio {
    private final CiudadanoHandler handler;
    public CiudadanoServicio(CiudadanoHandler handler) { this.handler = handler; }
    public List<Ciudadano> list() { return handler.list(); }
    public Ciudadano get(UUID id) { return handler.get(id); }
    public Ciudadano create(Ciudadano e) { return handler.create(e); }
    public Ciudadano update(UUID id, Ciudadano e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
