package com.ciudadania360.subsistemaia.client;

import com.ciudadania360.shared.ia.client.IAClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@Primary
public class HttpIAClient implements IAClient {

    @Value("${ia.provider.endpoint:http://localhost:9000}")
    private String endpoint;

    @Value("${ia.provider.api-key:}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();

    private String executePost(String path, Object body, boolean isBinary) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(endpoint + path);

            if (isBinary && body instanceof byte[] bytes) {
                post.setEntity(new ByteArrayEntity(bytes, ContentType.APPLICATION_OCTET_STREAM));
                post.setHeader("Content-Type", "application/octet-stream");
            } else {
                String json = mapper.writeValueAsString(body);
                post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
                post.setHeader("Content-Type", "application/json");
            }

            if (apiKey != null && !apiKey.isBlank()) {
                post.setHeader("Authorization", "Bearer " + apiKey);
            }

            return client.execute(post, r -> {
                var entity = r.getEntity();
                if (entity == null) return "";
                return new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
            });
        } catch (Exception e) {
            throw new RuntimeException("IA provider error (" + path + ")", e);
        }
    }

    @Override
    public String processText(String text, String task) {
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);
        body.put("task", task);
        return executePost("/v1/nlp", body, false);
    }

    @Override
    public String classifyDocument(String documentContent, String docType) {
        Map<String, Object> body = new HashMap<>();
        body.put("content", documentContent);
        body.put("type", docType);
        return executePost("/v1/classify", body, false);
    }

    @Override
    public String predict(String inputData, String model) {
        Map<String, Object> body = new HashMap<>();
        body.put("input", inputData);
        body.put("model", model);
        return executePost("/v1/predict", body, false);
    }

    @Override
    public String chat(UUID conversationId, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("conversationId", conversationId);
        body.put("message", message);
        return executePost("/v1/chat", body, false);
    }

    @Override
    public String transcribe(byte[] audioContent, String language) {
        return executePost("/v1/transcribe?lang=" + language, audioContent, true);
    }

    @Override
    public void train(String prompt, String expectedResponse) {
        // Si no tienes proveedor externo aún, puedes dejarlo simulado:
        log.info("Entrenando modelo con prompt: " + prompt + " y respuesta esperada: " + expectedResponse);

        // Si luego conectas a un proveedor externo, aquí harías la llamada HTTP correspondiente:
        // Map<String, Object> body = new HashMap<>();
        // body.put("prompt", prompt);
        // body.put("expected", expectedResponse);
        // executePost("/v1/train", body, false);
    }

}
