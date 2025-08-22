package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public NotificacionResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<NotificacionResponse> create(@RequestBody NotificacionRequest request) {
        NotificacionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public NotificacionResponse update(@PathVariable("id") UUID id, @RequestBody NotificacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
