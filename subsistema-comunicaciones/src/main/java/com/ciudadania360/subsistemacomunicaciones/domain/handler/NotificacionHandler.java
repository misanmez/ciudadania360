package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.NotificacionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class NotificacionHandler {
    private final NotificacionRepositorio repo;
    public NotificacionHandler(NotificacionRepositorio repo) { this.repo = repo; }

    public List<Notificacion> list() { return repo.findAll(); }
    public Notificacion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Notificacion no encontrado")); }
    public Notificacion create(Notificacion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Notificacion update(UUID id, Notificacion e) { Notificacion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
