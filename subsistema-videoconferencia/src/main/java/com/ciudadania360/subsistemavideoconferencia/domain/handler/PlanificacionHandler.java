package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.PlanificacionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PlanificacionHandler {
    private final PlanificacionRepositorio repo;
    public PlanificacionHandler(PlanificacionRepositorio repo) { this.repo = repo; }

    public List<Planificacion> list() { return repo.findAll(); }
    public Planificacion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Planificacion no encontrado")); }
    public Planificacion create(Planificacion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Planificacion update(UUID id, Planificacion e) { Planificacion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
