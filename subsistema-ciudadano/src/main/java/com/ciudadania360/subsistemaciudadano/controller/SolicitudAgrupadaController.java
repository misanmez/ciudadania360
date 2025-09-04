package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudAgrupadaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes-agrupadas")
public class SolicitudAgrupadaController {

    private final SolicitudAgrupadaService service;

    public SolicitudAgrupadaController(SolicitudAgrupadaService service) {
        this.service = service;
    }

    @GetMapping
    public List<SolicitudAgrupadaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public SolicitudAgrupadaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<SolicitudAgrupadaResponse> create(@Valid @RequestBody SolicitudAgrupadaRequest request) {
        SolicitudAgrupadaResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SolicitudAgrupadaResponse update(@PathVariable UUID id, @Valid @RequestBody SolicitudAgrupadaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
