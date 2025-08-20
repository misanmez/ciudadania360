package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.CarpetaServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/carpetas")
public class CarpetaController {
    private final CarpetaServicio service;
    public CarpetaController(CarpetaServicio service) { this.service = service; }

    @GetMapping
    public List<Carpeta> list() { return service.list(); }

    @GetMapping("/{id}")
    public Carpeta get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carpeta create(@RequestBody Carpeta e) { return service.create(e); }

    @PutMapping("/{id}")
    public Carpeta update(@PathVariable("id") UUID id, @RequestBody Carpeta e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
