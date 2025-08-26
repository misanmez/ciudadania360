package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.repository.InteraccionRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class InteraccionHandler {
    private final InteraccionRepository repositorio;

    public InteraccionHandler(InteraccionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Interaccion> list() {
        return repositorio.findAll();
    }

    public Interaccion get(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("Interaccion no encontrado"));
    }

    public Interaccion create(Interaccion e) {
        return repositorio.save(e);
    }

    public Interaccion update(UUID id, Interaccion cambios) {
        // Obtener la entidad existente
        Interaccion existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Interaccion no encontrado"));

        // Aplicar cambios a los campos modificables
        existente.setSolicitud(cambios.getSolicitud());
        existente.setCiudadano(cambios.getCiudadano());
        existente.setCanal(cambios.getCanal());
        existente.setFecha(cambios.getFecha());
        existente.setAgente(cambios.getAgente());
        existente.setMensaje(cambios.getMensaje());
        existente.setAdjuntoUri(cambios.getAdjuntoUri());
        existente.setVisibilidad(cambios.getVisibilidad());

        // Guardar la entidad modificada
        return repositorio.save(existente);
    }


    public void delete(UUID id) {
        repositorio.deleteById(id);
    }
}
