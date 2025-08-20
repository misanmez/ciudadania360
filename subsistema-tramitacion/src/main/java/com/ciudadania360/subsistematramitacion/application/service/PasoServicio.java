package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.handler.PasoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PasoServicio {
    private final PasoHandler handler;
    public PasoServicio(PasoHandler handler) { this.handler = handler; }
    public List<Paso> list() { return handler.list(); }
    public Paso get(UUID id) { return handler.get(id); }
    public Paso create(Paso e) { return handler.create(e); }
    public Paso update(UUID id, Paso e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
