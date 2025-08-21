package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ReglaClasificacionRepositorio;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class ReglaClasificacionHandler {
    private final ReglaClasificacionRepositorio repositorio;

    public ReglaClasificacionHandler(ReglaClasificacionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<ReglaClasificacion> list() {
        return repositorio.findAll();
    }

    public ReglaClasificacion get(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("ReglaClasificacion no encontrado"));
    }

    public ReglaClasificacion create(ReglaClasificacion e) {
        return repositorio.save(e);
    }

    public ReglaClasificacion update(UUID id, ReglaClasificacion cambios) {
        // Obtener la entidad existente
        ReglaClasificacion existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("ReglaClasificacion no encontrado"));

        // Aplicar cambios solo a los campos modificables
        existente.setNombre(cambios.getNombre());
        existente.setExpresion(cambios.getExpresion());
        existente.setPrioridad(cambios.getPrioridad());
        existente.setActiva(cambios.getActiva());
        existente.setClasificacionId(cambios.getClasificacionId());
        existente.setCondiciones(cambios.getCondiciones());
        existente.setFuente(cambios.getFuente());
        existente.setVigenciaDesde(cambios.getVigenciaDesde());
        existente.setVigenciaHasta(cambios.getVigenciaHasta());

        // Guardar la entidad modificada
        return repositorio.save(existente);
    }


    public void delete(UUID id) {
        repositorio.deleteById(id);
    }
}
