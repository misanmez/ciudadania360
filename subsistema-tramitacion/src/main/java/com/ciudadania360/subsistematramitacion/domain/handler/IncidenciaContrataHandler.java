package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.repository.IncidenciaContrataRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class IncidenciaContrataHandler {
    private final IncidenciaContrataRepository repo;
    public IncidenciaContrataHandler(IncidenciaContrataRepository repo) { this.repo = repo; }

    public List<IncidenciaContrata> list() { return repo.findAll(); }
    public IncidenciaContrata get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("IncidenciaContrata no encontrado")); }
    public IncidenciaContrata create(IncidenciaContrata e) { return repo.save(e); }
    public IncidenciaContrata update(UUID id, IncidenciaContrata e) { IncidenciaContrata cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
