package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudAgrupadaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SolicitudAgrupadaHandler {

    private final SolicitudAgrupadaRepository repository;

    public SolicitudAgrupadaHandler(SolicitudAgrupadaRepository repository) {
        this.repository = repository;
    }

    public List<SolicitudAgrupada> list() {
        return repository.findAll();
    }

    public SolicitudAgrupada get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud agrupada no encontrada con id: " + id));
    }

    public SolicitudAgrupada create(SolicitudAgrupada entity) {
        return repository.save(entity);
    }

    public SolicitudAgrupada update(UUID id, SolicitudAgrupada cambios) {
        SolicitudAgrupada existente = get(id);

        existente.setSolicitudPadre(cambios.getSolicitudPadre());
        existente.setSolicitudHija(cambios.getSolicitudHija());
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
