package com.ciudadania360.subsistemainterno.controller;

import com.ciudadania360.subsistemainterno.application.service.ParteTrabajoService;
import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoRequest;
import com.ciudadania360.subsistemainterno.application.dto.partetrabajo.ParteTrabajoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/partes-trabajo")
public class ParteTrabajoController {

    private final ParteTrabajoService service;

    public ParteTrabajoController(ParteTrabajoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ParteTrabajoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ParteTrabajoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ParteTrabajoResponse> create(@Valid @RequestBody ParteTrabajoRequest request) {
        ParteTrabajoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ParteTrabajoResponse update(@PathVariable("id") UUID id, @Valid @RequestBody ParteTrabajoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
