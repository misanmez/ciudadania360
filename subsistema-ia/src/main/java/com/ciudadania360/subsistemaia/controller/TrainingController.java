package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.service.ChatbotTrainingService;
import com.ciudadania360.shared.application.service.IATrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ia/training")
@RequiredArgsConstructor
public class TrainingController {

    private final ChatbotTrainingService chatbotTrainingService;
    private final IATrainingService iaTrainingService;

    /**
     * Genera ejemplos de entrenamiento a partir de los mensajes de chat guardados en BBDD.
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateExamples() {
        chatbotTrainingService.generateTrainingData();
        return ResponseEntity.ok("Ejemplos de entrenamiento generados desde las conversaciones.");
    }

    /**
     * Marca ejemplos específicos como entrenados (usado en backoffice/manual).
     */
    @PostMapping("/mark-trained")
    public ResponseEntity<String> markAsTrained(@RequestBody List<UUID> exampleIds) {
        chatbotTrainingService.markAsTrained(exampleIds);
        return ResponseEntity.ok("Ejemplos marcados como entrenados.");
    }

    /**
     * Entrena el modelo con los ejemplos nuevos disponibles.
     */
    @PostMapping("/train")
    public ResponseEntity<String> trainModel() {
        iaTrainingService.trainModel();
        return ResponseEntity.ok("Entrenamiento completado con ejemplos disponibles.");
    }
}

