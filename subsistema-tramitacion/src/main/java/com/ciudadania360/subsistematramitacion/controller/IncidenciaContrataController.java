package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataResponse;
import com.ciudadania360.subsistematramitacion.application.service.IncidenciaContrataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidencias-contrata")
public class IncidenciaContrataController {

    private final IncidenciaContrataService service;

    public IncidenciaContrataController(IncidenciaContrataService service) {
        this.service = service;
    }

    @GetMapping
    public List<IncidenciaContrataResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public IncidenciaContrataResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<IncidenciaContrataResponse> create(@RequestBody IncidenciaContrataRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public IncidenciaContrataResponse update(@PathVariable UUID id, @RequestBody IncidenciaContrataRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
