package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.handler.CarpetaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CarpetaServicio {
    private final CarpetaHandler handler;
    public CarpetaServicio(CarpetaHandler handler) { this.handler = handler; }
    public List<Carpeta> list() { return handler.list(); }
    public Carpeta get(UUID id) { return handler.get(id); }
    public Carpeta create(Carpeta e) { return handler.create(e); }
    public Carpeta update(UUID id, Carpeta e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
