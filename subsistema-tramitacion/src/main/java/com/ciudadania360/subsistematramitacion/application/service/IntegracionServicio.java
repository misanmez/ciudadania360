package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.handler.IntegracionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IntegracionServicio {
    private final IntegracionHandler handler;
    public IntegracionServicio(IntegracionHandler handler) { this.handler = handler; }
    public List<Integracion> list() { return handler.list(); }
    public Integracion get(UUID id) { return handler.get(id); }
    public Integracion create(Integracion e) { return handler.create(e); }
    public Integracion update(UUID id, Integracion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
