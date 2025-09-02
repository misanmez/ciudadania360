package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.domain.entity.IaTrainingExample;
import com.ciudadania360.shared.domain.repository.IaTrainingExampleRepository;
import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IaTrainingService {

    private final IAClient iaClient;
    private final IaTrainingExampleRepository trainingRepository;

    @Transactional
    public void trainModel() {
        // 1️⃣ Coger los ejemplos que no se han usado
        List<IaTrainingExample> examples = trainingRepository.findByUsedForTrainingFalse();

        if (examples.isEmpty()) {
            log.info("No hay ejemplos nuevos para entrenar.");
            return;
        }

        // 2️⃣ Preparar los datos de entrenamiento
        for (IaTrainingExample ex : examples) {
            // Para un proveedor externo, normalmente haríamos un POST a su API de entrenamiento
            // Para un modelo local, podemos guardarlo en un dataset o llamarlo directamente
            String prompt = ex.getUserMessage();
            String expected = ex.getResponse();

            // Ejemplo de entrenamiento simulado
            iaClient.train(prompt, expected);
        }

        // 3️⃣ Marcar ejemplos como entrenados
        examples.forEach(ex -> ex.setUsedForTraining(true));
        trainingRepository.saveAll(examples);

        log.info("Entrenamiento completado para {} ejemplos.", examples.size());
    }
}

