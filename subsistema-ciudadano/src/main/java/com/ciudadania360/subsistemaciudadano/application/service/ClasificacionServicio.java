package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ClasificacionHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ClasificacionServicio {
    private final ClasificacionHandler handler;

    public ClasificacionServicio(ClasificacionHandler handler) {
        this.handler = handler;
    }

    public List<Clasificacion> obtenerTodos() { return handler.obtenerTodos(); }
    public Clasificacion obtenerPorId(UUID id) { return handler.obtenerPorId(id); }
    public Clasificacion crear(Clasificacion e) { return handler.crear(e); }
    public Clasificacion actualizar(UUID id, Clasificacion e) { return handler.actualizar(id, e); }
    public void eliminar(UUID id) { handler.eliminar(id); }
}
