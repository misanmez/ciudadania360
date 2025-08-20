package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.repository.FlujoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class FlujoHandler {
    private final FlujoRepositorio repo;
    public FlujoHandler(FlujoRepositorio repo) { this.repo = repo; }

    public List<Flujo> list() { return repo.findAll(); }
    public Flujo get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Flujo no encontrado")); }
    public Flujo create(Flujo e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Flujo update(UUID id, Flujo e) { Flujo cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
