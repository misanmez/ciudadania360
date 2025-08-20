package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.DispositivoServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoController {
    private final DispositivoServicio service;
    public DispositivoController(DispositivoServicio service) { this.service = service; }

    @GetMapping
    public List<Dispositivo> list() { return service.list(); }

    @GetMapping("/{id}")
    public Dispositivo get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivo create(@RequestBody Dispositivo e) { return service.create(e); }

    @PutMapping("/{id}")
    public Dispositivo update(@PathVariable("id") UUID id, @RequestBody Dispositivo e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
