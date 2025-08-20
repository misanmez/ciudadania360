package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.handler.SolicitudHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SolicitudServicio {
    private final SolicitudHandler handler;

    public SolicitudServicio(SolicitudHandler handler) {
        this.handler = handler;
    }

    public List<Solicitud> obtenerTodos() { return handler.obtenerTodos(); }
    public Solicitud obtenerPorId(UUID id) { return handler.obtenerPorId(id); }
    public Solicitud crear(Solicitud e) { return handler.crear(e); }
    public Solicitud actualizar(UUID id, Solicitud e) { return handler.actualizar(id, e); }
    public void eliminar(UUID id) { handler.eliminar(id); }
}
