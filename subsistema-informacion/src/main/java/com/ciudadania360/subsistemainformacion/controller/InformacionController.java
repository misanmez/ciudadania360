package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.InformacionServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/informacions")
public class InformacionController {
    private final InformacionServicio service;
    public InformacionController(InformacionServicio service) { this.service = service; }

    @GetMapping
    public List<Informacion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Informacion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Informacion create(@RequestBody Informacion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Informacion update(@PathVariable("id") UUID id, @RequestBody Informacion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
