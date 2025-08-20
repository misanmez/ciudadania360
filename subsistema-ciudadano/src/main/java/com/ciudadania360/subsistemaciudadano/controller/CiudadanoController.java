package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ciudadanos")
public class CiudadanoController {
    private final CiudadanoServicio service;
    public CiudadanoController(CiudadanoServicio service) { this.service = service; }

    @GetMapping
    public List<Ciudadano> list() { return service.list(); }

    @GetMapping("/{id}")
    public Ciudadano get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    public ResponseEntity<Ciudadano> crear(@RequestBody Ciudadano e) {
        Ciudadano created = service.create(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Ciudadano update(@PathVariable("id") UUID id, @RequestBody Ciudadano e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
