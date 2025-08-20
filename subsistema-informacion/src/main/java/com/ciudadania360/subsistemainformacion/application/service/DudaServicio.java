package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.handler.DudaHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DudaServicio {
    private final DudaHandler handler;
    public DudaServicio(DudaHandler handler) { this.handler = handler; }
    public List<Duda> list() { return handler.list(); }
    public Duda get(UUID id) { return handler.get(id); }
    public Duda create(Duda e) { return handler.create(e); }
    public Duda update(UUID id, Duda e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
