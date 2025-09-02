package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.repository.RolRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RolHandler {
    private final RolRepository repo;
    public RolHandler(RolRepository repo) { this.repo = repo; }

    public List<Rol> list() { return repo.findAll(); }
    public Rol get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Rol no encontrado")); }
    public Rol create(Rol e) { return repo.save(e); }
    public Rol update(UUID id, Rol e) { Rol cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
