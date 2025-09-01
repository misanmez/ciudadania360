package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IAService {

    private final IAClient client;

    public String predict(String prompt, String model) {
        return client.predict(prompt, model);
    }

    public String processText(String text, String task) {
        return client.processText(text, task);
    }

    public String classifyDocument(String documentContent, String docType) {
        return client.classifyDocument(documentContent, docType);
    }

    public String transcribe(byte[] audioContent, String language) {
        return client.transcribe(audioContent, language);
    }
}
