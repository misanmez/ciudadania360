package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoResponse;
import com.ciudadania360.gestionrolespermisos.application.service.PermisoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {
    private final PermisoService service;

    public PermisoController(PermisoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PermisoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PermisoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<PermisoResponse> create(@RequestBody PermisoRequest request) {
        PermisoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public PermisoResponse update(@PathVariable("id") UUID id, @RequestBody PermisoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
