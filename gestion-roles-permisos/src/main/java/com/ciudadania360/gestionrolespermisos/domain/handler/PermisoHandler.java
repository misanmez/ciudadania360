package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.repository.PermisoRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class PermisoHandler {
    private final PermisoRepository repo;
    public PermisoHandler(PermisoRepository repo) { this.repo = repo; }

    public List<Permiso> list() { return repo.findAll(); }
    public Permiso get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Permiso no encontrado")); }
    public Permiso create(Permiso e) { return repo.save(e); }
    public Permiso update(UUID id, Permiso e) { Permiso cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
