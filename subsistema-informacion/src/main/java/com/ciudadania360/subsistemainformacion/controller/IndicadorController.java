package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.IndicadorServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/indicadors")
public class IndicadorController {
    private final IndicadorServicio service;
    public IndicadorController(IndicadorServicio service) { this.service = service; }

    @GetMapping
    public List<Indicador> list() { return service.list(); }

    @GetMapping("/{id}")
    public Indicador get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indicador create(@RequestBody Indicador e) { return service.create(e); }

    @PutMapping("/{id}")
    public Indicador update(@PathVariable("id") UUID id, @RequestBody Indicador e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
