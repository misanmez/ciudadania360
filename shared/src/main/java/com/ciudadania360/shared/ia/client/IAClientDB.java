package com.ciudadania360.shared.ia.client;

import com.ciudadania360.shared.domain.entity.IAChatMessage;
import com.ciudadania360.shared.domain.entity.IAConversation;
import com.ciudadania360.shared.domain.entity.IATrainingExample;
import com.ciudadania360.shared.domain.repository.IAChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IAConversationRepository;
import com.ciudadania360.shared.domain.repository.IATrainingExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class IAClientDB implements IAClient {

    private final IAChatMessageRepository chatMessageRepo;
    private final IAConversationRepository conversationRepo;
    private final IATrainingExampleRepository trainingExampleRepo;

    @Override
    @Transactional(readOnly = true)
    public String chat(UUID conversationId, String message) {
        // Buscar la conversación
        Optional<IAConversation> conv = conversationRepo.findById(conversationId);
        if (conv.isEmpty()) return "Conversación no encontrada";

        // Buscar la respuesta más cercana en chat messages
        List<IAChatMessage> messages = chatMessageRepo.findByConversationId(conv.get().getConversationId());
        return messages.stream()
                .filter(m -> m.getUserMessage().equalsIgnoreCase(message))
                .map(IAChatMessage::getResponse)
                .findFirst()
                .orElse("No hay respuesta disponible para este mensaje.");
    }

    @Override
    @Transactional(readOnly = true)
    public String processText(String text, String task) {
        // Buscar ejemplo en la BBDD según prompt y task
        return trainingExampleRepo.findByUserMessageContainingIgnoreCase(text)
                .stream()
                .map(IATrainingExample::getResponse)
                .filter(response -> response.toLowerCase().contains(task.toLowerCase()))
                .findFirst()
                .orElse("No hay resultado en la BBDD para esta tarea.");
    }

    @Override
    @Transactional(readOnly = true)
    public String classifyDocument(String documentContent, String docType) {
        return trainingExampleRepo.findAll()
                .stream()
                .filter(e -> e.getUserMessage().toLowerCase().contains(docType.toLowerCase())
                        && e.getResponse().toLowerCase().contains(documentContent.toLowerCase()))
                .map(IATrainingExample::getResponse)
                .findFirst()
                .orElse("Documento desconocido");
    }

    @Override
    @Transactional(readOnly = true)
    public String predict(String inputData, String model) {
        return trainingExampleRepo.findAll()
                .stream()
                .map(IATrainingExample::getResponse)
                .filter(response -> response.toLowerCase().contains(model.toLowerCase()))
                .findFirst()
                .orElse("No hay predicción disponible para este modelo");
    }

    @Override
    @Transactional(readOnly = true)
    public String transcribe(byte[] audioContent, String language) {
        return trainingExampleRepo.findAll()
                .stream()
                .map(IATrainingExample::getResponse)
                .filter(response -> response.toLowerCase().contains(language.toLowerCase()))
                .findFirst()
                .orElse("No hay transcripción disponible para este idioma");
    }

    @Override
    @Transactional
    public void train(String prompt, String expectedResponse) {
        IATrainingExample example = new IATrainingExample();
        example.setUserMessage(prompt);
        example.setResponse(expectedResponse);
        example.setUsedForTraining(true);
        trainingExampleRepo.save(example);
        log.info("Nuevo entrenamiento: '{}' -> '{}'", prompt, expectedResponse);
    }
}
