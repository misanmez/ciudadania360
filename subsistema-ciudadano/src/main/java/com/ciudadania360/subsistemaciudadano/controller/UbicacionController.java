package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.UbicacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ubicacions")
public class UbicacionController {
    private final UbicacionServicio servicio;

    public UbicacionController(UbicacionServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<Ubicacion>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtener(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Ubicacion> crear(@RequestBody Ubicacion e) {
        Ubicacion created = servicio.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> actualizar(@PathVariable("id") UUID id, @RequestBody Ubicacion e) {
        return ResponseEntity.ok(servicio.actualizar(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
