package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.handler.RecomendacionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecomendacionServicio {
    private final RecomendacionHandler handler;
    public RecomendacionServicio(RecomendacionHandler handler) { this.handler = handler; }
    public List<Recomendacion> list() { return handler.list(); }
    public Recomendacion get(UUID id) { return handler.get(id); }
    public Recomendacion create(Recomendacion e) { return handler.create(e); }
    public Recomendacion update(UUID id, Recomendacion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
