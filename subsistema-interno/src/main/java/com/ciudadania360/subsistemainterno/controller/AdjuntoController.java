package com.ciudadania360.subsistemainterno.controller;

import com.ciudadania360.subsistemainterno.application.service.AdjuntoService;
import com.ciudadania360.subsistemainterno.application.dto.adjunto.AdjuntoRequest;
import com.ciudadania360.subsistemainterno.application.dto.adjunto.AdjuntoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/adjuntos")
public class AdjuntoController {

    private final AdjuntoService service;

    public AdjuntoController(AdjuntoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdjuntoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public AdjuntoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<AdjuntoResponse> create(@Valid @RequestBody AdjuntoRequest request) {
        AdjuntoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public AdjuntoResponse update(@PathVariable("id") UUID id, @Valid @RequestBody AdjuntoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
