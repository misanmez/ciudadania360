package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.PasoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/pasos")
public class PasoController {
    private final PasoServicio service;
    public PasoController(PasoServicio service) { this.service = service; }

    @GetMapping
    public List<Paso> list() { return service.list(); }

    @GetMapping("/{id}")
    public Paso get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paso create(@RequestBody Paso e) { return service.create(e); }

    @PutMapping("/{id}")
    public Paso update(@PathVariable("id") UUID id, @RequestBody Paso e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
