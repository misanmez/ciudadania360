package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.domain.entity.IATrainingExample;
import com.ciudadania360.shared.domain.repository.IATrainingExampleRepository;
import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IATrainingService {

    private final IAClient iaClient;
    private final IATrainingExampleRepository trainingRepository;

    @Transactional
    public void trainModel() {
        // 1️⃣ Coger ejemplos no entrenados
        List<IATrainingExample> examples = trainingRepository.findByUsedForTrainingFalse();

        if (examples.isEmpty()) {
            log.info("No hay ejemplos nuevos para entrenar.");
            return;
        }

        // 2️⃣ Entrenar modelo IA
        for (IATrainingExample ex : examples) {
            iaClient.train(ex.getUserMessage(), ex.getResponse());
        }

        // 3️⃣ Marcar como entrenados
        examples.forEach(ex -> ex.setUsedForTraining(true));
        trainingRepository.saveAll(examples);

        log.info("Entrenamiento completado para {} ejemplos.", examples.size());
    }
}
