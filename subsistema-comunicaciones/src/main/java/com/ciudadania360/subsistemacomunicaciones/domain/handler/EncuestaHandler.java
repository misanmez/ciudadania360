package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.EncuestaRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EncuestaHandler {
    private final EncuestaRepositorio repo;
    public EncuestaHandler(EncuestaRepositorio repo) { this.repo = repo; }

    public List<Encuesta> list() { return repo.findAll(); }
    public Encuesta get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Encuesta no encontrado")); }
    public Encuesta create(Encuesta e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Encuesta update(UUID id, Encuesta e) { Encuesta cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
