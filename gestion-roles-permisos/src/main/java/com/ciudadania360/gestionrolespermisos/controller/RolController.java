package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.service.RolServicio;
import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/rols")
public class RolController {
    private final RolServicio service;
    public RolController(RolServicio service) { this.service = service; }

    @GetMapping
    public List<Rol> list() { return service.list(); }

    @GetMapping("/{id}")
    public Rol get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rol create(@RequestBody Rol e) { return service.create(e); }

    @PutMapping("/{id}")
    public Rol update(@PathVariable("id") UUID id, @RequestBody Rol e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
