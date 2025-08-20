package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.PlanificacionServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/planificacions")
public class PlanificacionController {
    private final PlanificacionServicio service;
    public PlanificacionController(PlanificacionServicio service) { this.service = service; }

    @GetMapping
    public List<Planificacion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Planificacion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Planificacion create(@RequestBody Planificacion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Planificacion update(@PathVariable("id") UUID id, @RequestBody Planificacion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
