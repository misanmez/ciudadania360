package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudRepositorio;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class SolicitudHandler {
    private final SolicitudRepositorio repositorio;

    public SolicitudHandler(SolicitudRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Solicitud> list() {
        return repositorio.findAll();
    }

    public Solicitud get(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrado"));
    }

    public Solicitud create(Solicitud e) {
        return repositorio.save(e);
    }

    public Solicitud update(UUID id, Solicitud cambios) {
        Solicitud existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrado"));

        // Aplicar cambios campo a campo
        existente.setCiudadano(cambios.getCiudadano());
        existente.setClasificacion(cambios.getClasificacion());
        existente.setUbicacion(cambios.getUbicacion());
        existente.setTitulo(cambios.getTitulo());
        existente.setDescripcion(cambios.getDescripcion());
        existente.setTipo(cambios.getTipo());
        existente.setCanalEntrada(cambios.getCanalEntrada());
        existente.setEstado(cambios.getEstado());
        existente.setPrioridad(cambios.getPrioridad());
        existente.setNumeroExpediente(cambios.getNumeroExpediente());
        existente.setFechaRegistro(cambios.getFechaRegistro());
        existente.setFechaLimiteSLA(cambios.getFechaLimiteSLA());
        existente.setFechaCierre(cambios.getFechaCierre());
        existente.setScoreRelevancia(cambios.getScoreRelevancia());
        existente.setOrigen(cambios.getOrigen());
        existente.setAdjuntosCount(cambios.getAdjuntosCount());
        existente.setEncuestaEnviada(cambios.getEncuestaEnviada());
        existente.setReferenciaExterna(cambios.getReferenciaExterna());
        existente.setMetadata(cambios.getMetadata());

        return repositorio.save(existente);
    }


    public void delete(UUID id) {
        repositorio.deleteById(id);
    }
}
