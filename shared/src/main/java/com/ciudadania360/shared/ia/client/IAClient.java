package com.ciudadania360.shared.ia.client;

import java.util.UUID;

public interface IAClient {

    String processText(String text, String task);

    String classifyDocument(String documentContent, String docType);

    String predict(String inputData, String model);

    String chat(UUID conversationId, String message);

    String transcribe(byte[] audioContent, String language);

    void train(String prompt, String expectedResponse);
}
