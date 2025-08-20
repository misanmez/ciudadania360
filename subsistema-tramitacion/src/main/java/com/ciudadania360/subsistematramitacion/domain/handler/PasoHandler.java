package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.repository.PasoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PasoHandler {
    private final PasoRepositorio repo;
    public PasoHandler(PasoRepositorio repo) { this.repo = repo; }

    public List<Paso> list() { return repo.findAll(); }
    public Paso get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Paso no encontrado")); }
    public Paso create(Paso e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Paso update(UUID id, Paso e) { Paso cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
