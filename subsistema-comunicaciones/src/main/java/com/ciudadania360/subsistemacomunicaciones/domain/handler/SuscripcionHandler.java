package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.SuscripcionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SuscripcionHandler {
    private final SuscripcionRepositorio repo;
    public SuscripcionHandler(SuscripcionRepositorio repo) { this.repo = repo; }

    public List<Suscripcion> list() { return repo.findAll(); }
    public Suscripcion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Suscripcion no encontrado")); }
    public Suscripcion create(Suscripcion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Suscripcion update(UUID id, Suscripcion e) { Suscripcion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
