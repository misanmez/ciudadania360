package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudEstadoHistorialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes-estado-historial")
public class SolicitudEstadoHistorialController {

    private final SolicitudEstadoHistorialService service;

    public SolicitudEstadoHistorialController(SolicitudEstadoHistorialService service) {
        this.service = service;
    }

    @GetMapping
    public List<SolicitudEstadoHistorialResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public SolicitudEstadoHistorialResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<SolicitudEstadoHistorialResponse> create(@Valid @RequestBody SolicitudEstadoHistorialRequest request) {
        SolicitudEstadoHistorialResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SolicitudEstadoHistorialResponse update(@PathVariable UUID id, @Valid @RequestBody SolicitudEstadoHistorialRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
