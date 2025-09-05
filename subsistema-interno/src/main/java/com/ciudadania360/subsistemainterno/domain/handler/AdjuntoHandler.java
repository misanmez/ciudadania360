package com.ciudadania360.subsistemainterno.domain.handler;

import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import com.ciudadania360.subsistemainterno.domain.repository.AdjuntoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AdjuntoHandler {

    private final AdjuntoRepository repository;

    public AdjuntoHandler(AdjuntoRepository repository) {
        this.repository = repository;
    }

    public List<Adjunto> list() {
        return repository.findAll();
    }

    public Adjunto get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adjunto no encontrado"));
    }

    public Adjunto create(Adjunto entity) {
        return repository.save(entity);
    }

    public Adjunto update(UUID id, Adjunto entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
