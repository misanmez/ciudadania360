package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.UbicacionHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UbicacionServicio {
    private final UbicacionHandler handler;

    public UbicacionServicio(UbicacionHandler handler) {
        this.handler = handler;
    }

    public List<Ubicacion> obtenerTodos() { return handler.obtenerTodos(); }
    public Ubicacion obtenerPorId(UUID id) { return handler.obtenerPorId(id); }
    public Ubicacion crear(Ubicacion e) { return handler.crear(e); }
    public Ubicacion actualizar(UUID id, Ubicacion e) { return handler.actualizar(id, e); }
    public void eliminar(UUID id) { handler.eliminar(id); }
}
