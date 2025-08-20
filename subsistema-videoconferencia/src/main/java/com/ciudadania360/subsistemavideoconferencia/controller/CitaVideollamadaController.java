package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.CitaVideollamadaServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/citavideollamadas")
public class CitaVideollamadaController {
    private final CitaVideollamadaServicio service;
    public CitaVideollamadaController(CitaVideollamadaServicio service) { this.service = service; }

    @GetMapping
    public List<CitaVideollamada> list() { return service.list(); }

    @GetMapping("/{id}")
    public CitaVideollamada get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaVideollamada create(@RequestBody CitaVideollamada e) { return service.create(e); }

    @PutMapping("/{id}")
    public CitaVideollamada update(@PathVariable("id") UUID id, @RequestBody CitaVideollamada e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
