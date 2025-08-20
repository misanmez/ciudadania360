package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.repository.CiudadanoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CiudadanoHandler {
    private final CiudadanoRepositorio repo;
    public CiudadanoHandler(CiudadanoRepositorio repo) { this.repo = repo; }

    public List<Ciudadano> list() { return repo.findAll(); }
    public Ciudadano get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Ciudadano no encontrado")); }
    public Ciudadano create(Ciudadano e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Ciudadano update(UUID id, Ciudadano e) { Ciudadano cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
