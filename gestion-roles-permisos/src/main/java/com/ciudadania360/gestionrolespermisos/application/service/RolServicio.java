package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.handler.RolHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RolServicio {
    private final RolHandler handler;
    public RolServicio(RolHandler handler) { this.handler = handler; }
    public List<Rol> list() { return handler.list(); }
    public Rol get(UUID id) { return handler.get(id); }
    public Rol create(Rol e) { return handler.create(e); }
    public Rol update(UUID id, Rol e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
