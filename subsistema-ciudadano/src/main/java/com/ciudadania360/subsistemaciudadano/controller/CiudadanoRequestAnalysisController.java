package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoRequestAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ciudadano/requests")
@RequiredArgsConstructor
public class CiudadanoRequestAnalysisController {

    private final CiudadanoRequestAnalysisService service;

    @PostMapping("/classify")
    public String classify(@RequestParam String content) {
        return service.classifyRequest(content);
    }
}
