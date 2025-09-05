package com.ciudadania360.subsistemainterno.domain.handler;

import com.ciudadania360.shared.domain.entity.Empleado;
import com.ciudadania360.subsistemainterno.domain.repository.EmpleadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EmpleadoHandler {

    private final EmpleadoRepository repository;

    public EmpleadoHandler(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public List<Empleado> list() {
        return repository.findAll();
    }

    public Empleado get(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public Empleado create(Empleado entity) {
        return repository.save(entity);
    }

    public Empleado update(UUID id, Empleado entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
