package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ClasificacionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clasificaciones")
@RequiredArgsConstructor
public class ClasificacionController {

    private final ClasificacionService service;

    @GetMapping
    public List<ClasificacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ClasificacionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClasificacionResponse create(@RequestBody ClasificacionRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ClasificacionResponse update(@PathVariable UUID id,
                                        @RequestBody ClasificacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
