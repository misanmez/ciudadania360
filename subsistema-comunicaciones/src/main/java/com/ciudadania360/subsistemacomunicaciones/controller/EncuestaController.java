package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.EncuestaService;
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
    public EncuestaResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<EncuestaResponse> create(@RequestBody EncuestaRequest request) {
        EncuestaResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public EncuestaResponse update(@PathVariable("id") UUID id, @RequestBody EncuestaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
