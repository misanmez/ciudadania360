package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadanoRecommendationService {

    private final IAService iaService;

    public List<String> suggestTramites(String historialUsuario) {
        String prediction = iaService.predict(historialUsuario, "tramite-recommendation-model");
        // Se puede parsear JSON o CSV según el proveedor de IA
        return List.of(prediction.split(","));
    }
}
