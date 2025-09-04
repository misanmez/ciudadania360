package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudEstadoHistorialRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SolicitudEstadoHistorialHandler {

    private final SolicitudEstadoHistorialRepository repository;

    public SolicitudEstadoHistorialHandler(SolicitudEstadoHistorialRepository repository) {
        this.repository = repository;
    }

    public List<SolicitudEstadoHistorial> list() {
        return repository.findAll();
    }

    public SolicitudEstadoHistorial get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial de estado no encontrado con id: " + id));
    }

    public SolicitudEstadoHistorial create(SolicitudEstadoHistorial entity) {
        return repository.save(entity);
    }

    public SolicitudEstadoHistorial update(UUID id, SolicitudEstadoHistorial cambios) {
        SolicitudEstadoHistorial existente = get(id);

        existente.setSolicitud(cambios.getSolicitud());
        existente.setEstadoAnterior(cambios.getEstadoAnterior());
        existente.setEstadoNuevo(cambios.getEstadoNuevo());
        existente.setFechaCambio(cambios.getFechaCambio());
        existente.setAgente(cambios.getAgente());
        existente.setMetadata(cambios.getMetadata());

        return repository.save(existente);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public boolean exists(UUID id) {
        return repository.existsById(id);
    }
}
