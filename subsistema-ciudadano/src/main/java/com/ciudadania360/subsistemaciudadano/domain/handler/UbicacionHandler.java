package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.UbicacionRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class UbicacionHandler {
    private final UbicacionRepository repositorio;

    public UbicacionHandler(UbicacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Ubicacion> list() {
        return repositorio.findAll();
    }

    public Ubicacion get(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("Ubicacion no encontrado"));
    }

    public Ubicacion create(Ubicacion e) {
        return repositorio.save(e);
    }

    public Ubicacion update(UUID id, Ubicacion cambios) {
        Ubicacion existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrado"));

        // Aplicar cambios sobre la entidad existente
        existente.setDireccion(cambios.getDireccion());
        existente.setMunicipio(cambios.getMunicipio());
        existente.setProvincia(cambios.getProvincia());
        existente.setCp(cambios.getCp());
        existente.setLat(cambios.getLat());
        existente.setLon(cambios.getLon());
        existente.setPrecision(cambios.getPrecision());
        existente.setFuente(cambios.getFuente());

        return repositorio.save(existente);
    }

    public void delete(UUID id) {
        repositorio.deleteById(id);
    }
}
