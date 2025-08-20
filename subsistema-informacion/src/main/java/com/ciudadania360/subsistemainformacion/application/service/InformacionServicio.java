package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.handler.InformacionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class InformacionServicio {
    private final InformacionHandler handler;
    public InformacionServicio(InformacionHandler handler) { this.handler = handler; }
    public List<Informacion> list() { return handler.list(); }
    public Informacion get(UUID id) { return handler.get(id); }
    public Informacion create(Informacion e) { return handler.create(e); }
    public Informacion update(UUID id, Informacion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
