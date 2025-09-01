package com.ciudadania360.shared.ia.client;

public interface IAClient {

    String processText(String text, String task);

    String classifyDocument(String documentContent, String docType);

    String predict(String inputData, String model);

    String chat(String conversationId, String message);

    String transcribe(byte[] audioContent, String language);
}
