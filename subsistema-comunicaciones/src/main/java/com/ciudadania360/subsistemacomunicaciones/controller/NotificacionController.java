package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.NotificacionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/notificacions")
public class NotificacionController {
    private final NotificacionServicio service;
    public NotificacionController(NotificacionServicio service) { this.service = service; }

    @GetMapping
    public List<Notificacion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Notificacion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notificacion create(@RequestBody Notificacion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Notificacion update(@PathVariable("id") UUID id, @RequestBody Notificacion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
