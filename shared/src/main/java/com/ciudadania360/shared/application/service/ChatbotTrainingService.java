package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.domain.entity.IaChatMessage;
import com.ciudadania360.shared.domain.entity.IaTrainingExample;
import com.ciudadania360.shared.domain.repository.IaChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IaTrainingExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatbotTrainingService {

    private final IaChatMessageRepository chatMessageRepository;
    private final IaTrainingExampleRepository trainingRepository;

    @Transactional
    public void generateTrainingData() {
        // 1️⃣ Leer todos los mensajes que aún no se han usado
        List<IaChatMessage> messages = chatMessageRepository.findAll();

        for (IaChatMessage msg : messages) {
            // 2️⃣ Guardar como ejemplo de entrenamiento si no existe
            IaTrainingExample example = IaTrainingExample.builder()
                    .id(msg.getConversation().getConversationId())
                    .userMessage(msg.getUserMessage())
                    .response(msg.getResponse())
                    .usedForTraining(false)
                    .build();
            trainingRepository.save(example);
        }
    }

    @Transactional
    public void markAsTrained(List<UUID> exampleIds) {
        // Marcar ejemplos ya usados
        List<IaTrainingExample> examples = trainingRepository.findAllById(exampleIds);
        examples.forEach(ex -> ex.setUsedForTraining(true));
        trainingRepository.saveAll(examples);
    }
}
