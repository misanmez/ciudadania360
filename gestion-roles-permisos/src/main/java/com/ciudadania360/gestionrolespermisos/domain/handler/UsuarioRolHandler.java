package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import com.ciudadania360.gestionrolespermisos.domain.repository.UsuarioRolRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class UsuarioRolHandler {
    private final UsuarioRolRepositorio repo;
    public UsuarioRolHandler(UsuarioRolRepositorio repo) { this.repo = repo; }

    public List<UsuarioRol> list() { return repo.findAll(); }
    public UsuarioRol get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("UsuarioRol no encontrado")); }
    public UsuarioRol create(UsuarioRol e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public UsuarioRol update(UUID id, UsuarioRol e) { UsuarioRol cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
