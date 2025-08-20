package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ReglaClasificacionHandler;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ReglaClasificacionServicio {
    private final ReglaClasificacionHandler handler;

    public ReglaClasificacionServicio(ReglaClasificacionHandler handler) {
        this.handler = handler;
    }

    public List<ReglaClasificacion> obtenerTodos() { return handler.obtenerTodos(); }
    public ReglaClasificacion obtenerPorId(UUID id) { return handler.obtenerPorId(id); }
    public ReglaClasificacion crear(ReglaClasificacion e) { return handler.crear(e); }
    public ReglaClasificacion actualizar(UUID id, ReglaClasificacion e) { return handler.actualizar(id, e); }
    public void eliminar(UUID id) { handler.eliminar(id); }
}
