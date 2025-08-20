package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.PlanificacionHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PlanificacionServicio {
    private final PlanificacionHandler handler;
    public PlanificacionServicio(PlanificacionHandler handler) { this.handler = handler; }
    public List<Planificacion> list() { return handler.list(); }
    public Planificacion get(UUID id) { return handler.get(id); }
    public Planificacion create(Planificacion e) { return handler.create(e); }
    public Planificacion update(UUID id, Planificacion e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
