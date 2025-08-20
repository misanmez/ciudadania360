package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepositorio;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class ClasificacionHandler {
    private final ClasificacionRepositorio repositorio;

    public ClasificacionHandler(ClasificacionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Clasificacion> obtenerTodos() {
        return repositorio.findAll();
    }

    public Clasificacion obtenerPorId(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("Clasificacion no encontrado"));
    }

    public Clasificacion crear(Clasificacion e) {
        return repositorio.save(e);
    }

    public Clasificacion actualizar(UUID id, Clasificacion e) {
        if (!repositorio.existsById(id)) throw new RuntimeException("Clasificacion no encontrado");
        e.setId(id);
        return repositorio.save(e);
    }

    public void eliminar(UUID id) {
        repositorio.deleteById(id);
    }
}
