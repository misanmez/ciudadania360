package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.ProcesoBPMServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/procesobpms")
public class ProcesoBPMController {
    private final ProcesoBPMServicio service;
    public ProcesoBPMController(ProcesoBPMServicio service) { this.service = service; }

    @GetMapping
    public List<ProcesoBPM> list() { return service.list(); }

    @GetMapping("/{id}")
    public ProcesoBPM get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProcesoBPM create(@RequestBody ProcesoBPM e) { return service.create(e); }

    @PutMapping("/{id}")
    public ProcesoBPM update(@PathVariable("id") UUID id, @RequestBody ProcesoBPM e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
