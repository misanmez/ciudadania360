package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.repository.DireccionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DireccionHandler {
    private final DireccionRepositorio repo;
    public DireccionHandler(DireccionRepositorio repo) { this.repo = repo; }

    public List<Direccion> list() { return repo.findAll(); }
    public Direccion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Direccion no encontrado")); }
    public Direccion create(Direccion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Direccion update(UUID id, Direccion e) { Direccion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
