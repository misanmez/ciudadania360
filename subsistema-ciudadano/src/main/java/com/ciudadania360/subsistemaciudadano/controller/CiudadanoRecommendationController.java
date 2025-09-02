package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ciudadano/recommendations")
@RequiredArgsConstructor
public class CiudadanoRecommendationController {

    private final CiudadanoRecommendationService service;

    @GetMapping
    public ResponseEntity<List<String>> getTramiteRecommendations(@RequestParam String userHistory) {
        return ResponseEntity.ok(service.suggestTramites(userHistory));
    }
}

