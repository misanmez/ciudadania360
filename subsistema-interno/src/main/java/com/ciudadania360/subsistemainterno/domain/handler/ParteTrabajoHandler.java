package com.ciudadania360.subsistemainterno.domain.handler;

import com.ciudadania360.subsistemainterno.domain.entity.ParteTrabajo;
import com.ciudadania360.subsistemainterno.domain.repository.ParteTrabajoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ParteTrabajoHandler {

    private final ParteTrabajoRepository repository;

    public ParteTrabajoHandler(ParteTrabajoRepository repository) {
        this.repository = repository;
    }

    public List<ParteTrabajo> list() {
        return repository.findAll();
    }

    public ParteTrabajo get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parte de trabajo no encontrado"));
    }

    public ParteTrabajo create(ParteTrabajo entity) {
        return repository.save(entity);
    }

    public ParteTrabajo update(UUID id, ParteTrabajo entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
