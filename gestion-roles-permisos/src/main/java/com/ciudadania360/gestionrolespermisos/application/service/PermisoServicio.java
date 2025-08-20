package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.handler.PermisoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PermisoServicio {
    private final PermisoHandler handler;
    public PermisoServicio(PermisoHandler handler) { this.handler = handler; }
    public List<Permiso> list() { return handler.list(); }
    public Permiso get(UUID id) { return handler.get(id); }
    public Permiso create(Permiso e) { return handler.create(e); }
    public Permiso update(UUID id, Permiso e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
