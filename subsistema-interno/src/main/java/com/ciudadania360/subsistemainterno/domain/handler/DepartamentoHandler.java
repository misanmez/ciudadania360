package com.ciudadania360.subsistemainterno.domain.handler;

import com.ciudadania360.subsistemainterno.domain.entity.Departamento;
import com.ciudadania360.subsistemainterno.domain.repository.DepartamentoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DepartamentoHandler {

    private final DepartamentoRepository repository;

    public DepartamentoHandler(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> list() {
        return repository.findAll();
    }

    public Departamento get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
    }

    public Departamento create(Departamento entity) {
        return repository.save(entity);
    }

    public Departamento update(UUID id, Departamento entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
