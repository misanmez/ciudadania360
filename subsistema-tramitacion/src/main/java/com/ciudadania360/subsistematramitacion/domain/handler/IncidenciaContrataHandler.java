package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.repository.IncidenciaContrataRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class IncidenciaContrataHandler {
    private final IncidenciaContrataRepositorio repo;
    public IncidenciaContrataHandler(IncidenciaContrataRepositorio repo) { this.repo = repo; }

    public List<IncidenciaContrata> list() { return repo.findAll(); }
    public IncidenciaContrata get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("IncidenciaContrata no encontrado")); }
    public IncidenciaContrata create(IncidenciaContrata e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public IncidenciaContrata update(UUID id, IncidenciaContrata e) { IncidenciaContrata cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
