package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import com.ciudadania360.subsistemaciudadano.domain.repository.EncuestaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EncuestaHandler {

    private final EncuestaRepository repository;

    public EncuestaHandler(EncuestaRepository repository) {
        this.repository = repository;
    }

    public List<Encuesta> list() {
        return repository.findAll();
    }

    public Encuesta get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
    }

    public Encuesta create(Encuesta e) {
        return repository.save(e);
    }

    public Encuesta update(UUID id, Encuesta cambios) {
        Encuesta existente = get(id);

        // Actualizamos solo los campos editables
        existente.setSolicitud(cambios.getSolicitud());
        existente.setTipo(cambios.getTipo());
        existente.setEstado(cambios.getEstado());
        existente.setFechaEnvio(cambios.getFechaEnvio());
        existente.setFechaRespuesta(cambios.getFechaRespuesta());
        existente.setRespuestas(cambios.getRespuestas());
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
