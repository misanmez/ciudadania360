package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.CitaVideollamadaRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CitaVideollamadaHandler {
    private final CitaVideollamadaRepositorio repo;
    public CitaVideollamadaHandler(CitaVideollamadaRepositorio repo) { this.repo = repo; }

    public List<CitaVideollamada> list() { return repo.findAll(); }
    public CitaVideollamada get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("CitaVideollamada no encontrado")); }
    public CitaVideollamada create(CitaVideollamada e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public CitaVideollamada update(UUID id, CitaVideollamada e) { CitaVideollamada cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
