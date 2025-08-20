package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.repository.DudaRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DudaHandler {
    private final DudaRepositorio repo;
    public DudaHandler(DudaRepositorio repo) { this.repo = repo; }

    public List<Duda> list() { return repo.findAll(); }
    public Duda get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Duda no encontrado")); }
    public Duda create(Duda e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Duda update(UUID id, Duda e) { Duda cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
