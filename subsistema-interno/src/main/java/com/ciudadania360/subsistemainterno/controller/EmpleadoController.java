package com.ciudadania360.subsistemainterno.controller;

import com.ciudadania360.subsistemainterno.application.service.EmpleadoService;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoRequest;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmpleadoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public EmpleadoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> create(@Valid @RequestBody EmpleadoRequest request) {
        EmpleadoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public EmpleadoResponse update(@PathVariable("id") UUID id, @Valid @RequestBody EmpleadoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
