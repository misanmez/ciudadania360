package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Notificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.NotificacionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class NotificacionHandler {

    private final NotificacionRepository repository;

    public NotificacionHandler(NotificacionRepository repository) {
        this.repository = repository;
    }

    public List<Notificacion> list() {
        return repository.findAll();
    }

    public Notificacion get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));
    }

    public Notificacion create(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    public Notificacion update(UUID id, Notificacion cambios) {
        Notificacion existente = get(id);

        // Actualizar solo campos modificables
        existente.setSolicitud(cambios.getSolicitud());
        existente.setCanal(cambios.getCanal());
        existente.setFechaEnvio(cambios.getFechaEnvio());
        existente.setEstado(cambios.getEstado());
        existente.setMensaje(cambios.getMensaje());
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
