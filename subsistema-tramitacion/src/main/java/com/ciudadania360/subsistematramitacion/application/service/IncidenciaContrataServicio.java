package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.handler.IncidenciaContrataHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IncidenciaContrataServicio {
    private final IncidenciaContrataHandler handler;
    public IncidenciaContrataServicio(IncidenciaContrataHandler handler) { this.handler = handler; }
    public List<IncidenciaContrata> list() { return handler.list(); }
    public IncidenciaContrata get(UUID id) { return handler.get(id); }
    public IncidenciaContrata create(IncidenciaContrata e) { return handler.create(e); }
    public IncidenciaContrata update(UUID id, IncidenciaContrata e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
