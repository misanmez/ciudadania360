package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.TareaBPMServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/tareabpms")
public class TareaBPMController {
    private final TareaBPMServicio service;
    public TareaBPMController(TareaBPMServicio service) { this.service = service; }

    @GetMapping
    public List<TareaBPM> list() { return service.list(); }

    @GetMapping("/{id}")
    public TareaBPM get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaBPM create(@RequestBody TareaBPM e) { return service.create(e); }

    @PutMapping("/{id}")
    public TareaBPM update(@PathVariable("id") UUID id, @RequestBody TareaBPM e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
