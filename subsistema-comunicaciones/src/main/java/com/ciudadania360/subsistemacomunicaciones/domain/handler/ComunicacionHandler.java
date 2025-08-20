package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.ComunicacionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ComunicacionHandler {
    private final ComunicacionRepositorio repo;
    public ComunicacionHandler(ComunicacionRepositorio repo) { this.repo = repo; }

    public List<Comunicacion> list() { return repo.findAll(); }
    public Comunicacion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Comunicacion no encontrado")); }
    public Comunicacion create(Comunicacion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Comunicacion update(UUID id, Comunicacion e) { Comunicacion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
