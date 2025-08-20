package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.handler.InteraccionHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class InteraccionServicio {
    private final InteraccionHandler handler;

    public InteraccionServicio(InteraccionHandler handler) {
        this.handler = handler;
    }

    public List<Interaccion> obtenerTodos() { return handler.obtenerTodos(); }
    public Interaccion obtenerPorId(UUID id) { return handler.obtenerPorId(id); }
    public Interaccion crear(Interaccion e) { return handler.crear(e); }
    public Interaccion actualizar(UUID id, Interaccion e) { return handler.actualizar(id, e); }
    public void eliminar(UUID id) { handler.eliminar(id); }
}
