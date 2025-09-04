package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemaciudadano.application.service.EncuestaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    private final EncuestaService service;

    public EncuestaController(EncuestaService service) {
        this.service = service;
    }

    @GetMapping
    public List<EncuestaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public EncuestaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<EncuestaResponse> create(@Valid @RequestBody EncuestaRequest request) {
        EncuestaResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public EncuestaResponse update(@PathVariable UUID id, @Valid @RequestBody EncuestaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
