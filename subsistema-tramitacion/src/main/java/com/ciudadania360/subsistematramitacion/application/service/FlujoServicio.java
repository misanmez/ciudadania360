package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.handler.FlujoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FlujoServicio {
    private final FlujoHandler handler;
    public FlujoServicio(FlujoHandler handler) { this.handler = handler; }
    public List<Flujo> list() { return handler.list(); }
    public Flujo get(UUID id) { return handler.get(id); }
    public Flujo create(Flujo e) { return handler.create(e); }
    public Flujo update(UUID id, Flujo e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
