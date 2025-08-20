package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.SugerenciaServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/sugerencias")
public class SugerenciaController {
    private final SugerenciaServicio service;
    public SugerenciaController(SugerenciaServicio service) { this.service = service; }

    @GetMapping
    public List<Sugerencia> list() { return service.list(); }

    @GetMapping("/{id}")
    public Sugerencia get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sugerencia create(@RequestBody Sugerencia e) { return service.create(e); }

    @PutMapping("/{id}")
    public Sugerencia update(@PathVariable("id") UUID id, @RequestBody Sugerencia e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
