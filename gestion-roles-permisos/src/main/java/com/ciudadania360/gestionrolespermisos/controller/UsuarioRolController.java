package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.service.UsuarioRolServicio;
import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/usuariorols")
public class UsuarioRolController {
    private final UsuarioRolServicio service;
    public UsuarioRolController(UsuarioRolServicio service) { this.service = service; }

    @GetMapping
    public List<UsuarioRol> list() { return service.list(); }

    @GetMapping("/{id}")
    public UsuarioRol get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioRol create(@RequestBody UsuarioRol e) { return service.create(e); }

    @PutMapping("/{id}")
    public UsuarioRol update(@PathVariable("id") UUID id, @RequestBody UsuarioRol e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
