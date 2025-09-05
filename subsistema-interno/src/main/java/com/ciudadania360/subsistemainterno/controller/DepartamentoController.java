package com.ciudadania360.subsistemainterno.controller;

import com.ciudadania360.subsistemainterno.application.service.DepartamentoService;
import com.ciudadania360.subsistemainterno.application.dto.departamento.DepartamentoRequest;
import com.ciudadania360.subsistemainterno.application.dto.departamento.DepartamentoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoService service;

    public DepartamentoController(DepartamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DepartamentoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public DepartamentoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponse> create(@Valid @RequestBody DepartamentoRequest request) {
        DepartamentoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public DepartamentoResponse update(@PathVariable("id") UUID id, @Valid @RequestBody DepartamentoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
