package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.*;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicituds")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @GetMapping
    public List<SolicitudResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public SolicitudResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<SolicitudResponse> create(@RequestBody SolicitudRequest request) {
        SolicitudResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SolicitudResponse update(@PathVariable UUID id, @RequestBody SolicitudRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/transition")
    public SolicitudResponse transition(@PathVariable UUID id, @RequestBody SolicitudTransitionRequest req) {
        return service.transition(id, req.getNuevoEstado());
    }

    @PostMapping("/{id}/recalculate-sla")
    public SolicitudResponse recalcSla(@PathVariable UUID id) {
        return service.recalculateSla(id);
    }

    @PostMapping("/{id}/assign")
    public SolicitudResponse assign(@PathVariable UUID id, @RequestBody SolicitudAssignRequest req) {
        return service.assign(id, req.getAgenteId());
    }

    @PostMapping("/search")
    public List<SolicitudResponse> search(@RequestBody SolicitudSearchFilter filter) {
        return service.search(filter);
    }

    @PostMapping("/{id}/classify-auto")
    public SolicitudResponse classifyAuto(@PathVariable UUID id) {
        return service.classifyAuto(id);
    }
}
