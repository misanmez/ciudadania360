package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.ClasificacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clasificacions")
public class ClasificacionController {
    private final ClasificacionServicio servicio;

    public ClasificacionController(ClasificacionServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<Clasificacion>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clasificacion> obtener(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Clasificacion> crear(@RequestBody Clasificacion e) {
        Clasificacion created = servicio.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clasificacion> actualizar(@PathVariable("id") UUID id, @RequestBody Clasificacion e) {
        return ResponseEntity.ok(servicio.actualizar(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
