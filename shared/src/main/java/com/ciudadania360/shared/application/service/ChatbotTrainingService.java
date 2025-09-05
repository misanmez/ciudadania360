package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.domain.entity.IAChatMessage;
import com.ciudadania360.shared.domain.entity.IATrainingExample;
import com.ciudadania360.shared.domain.repository.IAChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IATrainingExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatbotTrainingService {

    private final IAChatMessageRepository chatMessageRepository;
    private final IATrainingExampleRepository trainingRepository;

    @Transactional
    public void generateTrainingData() {
        // 1️⃣ Leer mensajes no transformados en ejemplos
        List<IAChatMessage> messages = chatMessageRepository.findAll();

        for (IAChatMessage msg : messages) {
            // 2️⃣ Crear ejemplo si no existe
            IATrainingExample example = IATrainingExample.builder()
                    .id(msg.getConversation().getId())
                    .userMessage(msg.getUserMessage())
                    .response(msg.getResponse())
                    .usedForTraining(false)
                    .build();
            trainingRepository.save(example);
        }
    }

    @Transactional
    public void markAsTrained(List<UUID> exampleIds) {
        List<IATrainingExample> examples = trainingRepository.findAllById(exampleIds);
        examples.forEach(ex -> ex.setUsedForTraining(true));
        trainingRepository.saveAll(examples);
    }
}
