package com.ciudadania360.shared.ia.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class IAClientMock implements IAClient {

    private final Map<String, String> faq = new HashMap<>();

    public IAClientMock() {
        // Base de conocimiento mínima del Chatbot
        faq.put("dni", "Para renovar el DNI necesitas: 1) Una foto reciente, 2) El pago de la tasa, 3) El DNI anterior. " +
                "Si se ha extraviado, deberás presentar una denuncia.");
        faq.put("pasaporte", "Para solicitar el pasaporte necesitas: 1) El DNI en vigor, 2) Una foto reciente, 3) El pago de la tasa correspondiente.");
        faq.put("empadronamiento", "Para empadronarte necesitas: 1) Documento de identidad (DNI/NIE), 2) Contrato de alquiler o escritura de vivienda, " +
                "3) Autorización del propietario si no eres titular de la vivienda.");
    }

    @Override
    public String processText(String text, String task) {
        // Simula distintas tareas de NLP
        return switch (task.toLowerCase()) {
            case "resumir" -> "Resumen: " + (text.length() > 50 ? text.substring(0, 50) + "..." : text);
            case "traducir" -> "Traducción simulada (ES→EN): " + text.replace("hola", "hello").replace("mundo", "world");
            case "sentimiento" -> text.toLowerCase().contains("bien") ? "positivo" :
                    text.toLowerCase().contains("mal") ? "negativo" : "neutral";
            default -> "Tarea '" + task + "' no soportada en mock.";
        };
    }

    @Override
    public String classifyDocument(String documentContent, String docType) {
        // Mock de clasificación básica
        if (docType.equalsIgnoreCase("identidad")) {
            if (documentContent.toLowerCase().contains("dni")) return "DNI";
            if (documentContent.toLowerCase().contains("pasaporte")) return "Pasaporte";
        }
        if (docType.equalsIgnoreCase("domicilio")) {
            if (documentContent.toLowerCase().contains("factura")) return "Factura de suministros";
            if (documentContent.toLowerCase().contains("contrato")) return "Contrato de alquiler";
        }
        return "Documento desconocido";
    }

    @Override
    public String predict(String inputData, String model) {
        // Simulación de predicciones con modelos distintos
        return switch (model.toLowerCase()) {
            case "riesgo" -> inputData.contains("impago") ? "Alto riesgo" : "Bajo riesgo";
            case "prioridad" -> inputData.length() > 100 ? "Alta prioridad" : "Prioridad normal";
            case "satisfaccion" -> inputData.toLowerCase().contains("queja") ? "Satisfacción baja" : "Satisfacción media/alta";
            default -> "Modelo '" + model + "' no soportado en mock.";
        };
    }

    @Override
    public String chat(UUID conversationId, String message) {
        // Chatbot FAQ básico
        String lower = message.toLowerCase();
        for (Map.Entry<String, String> entry : faq.entrySet()) {
            if (lower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "Lo siento, aún no tengo información sobre esa consulta.";
    }

    @Override
    public String transcribe(byte[] audioContent, String language) {
        // Mock de transcripción (no interpreta audio real, solo simula)
        return switch (language.toLowerCase()) {
            case "es" -> "Transcripción simulada en español: [texto de prueba]";
            case "en" -> "Mocked transcription in English: [sample text]";
            default -> "Idioma no soportado en mock.";
        };
    }

    @Override
    public void train(String prompt, String expectedResponse) {
        log.info("Training example: '" + prompt + "' -> '" + expectedResponse + "'");
    }
}
