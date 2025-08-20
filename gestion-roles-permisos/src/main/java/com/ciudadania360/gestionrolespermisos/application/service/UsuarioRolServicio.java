package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import com.ciudadania360.gestionrolespermisos.domain.handler.UsuarioRolHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UsuarioRolServicio {
    private final UsuarioRolHandler handler;
    public UsuarioRolServicio(UsuarioRolHandler handler) { this.handler = handler; }
    public List<UsuarioRol> list() { return handler.list(); }
    public UsuarioRol get(UUID id) { return handler.get(id); }
    public UsuarioRol create(UsuarioRol e) { return handler.create(e); }
    public UsuarioRol update(UUID id, UsuarioRol e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
