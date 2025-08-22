package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.PlanificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/planificaciones")
public class PlanificacionController {

    private final PlanificacionService service;

    public PlanificacionController(PlanificacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlanificacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PlanificacionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<PlanificacionResponse> create(@RequestBody PlanificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public PlanificacionResponse update(@PathVariable UUID id, @RequestBody PlanificacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
