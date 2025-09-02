package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.application.dto.RequestDto;
import com.ciudadania360.shared.application.dto.ResponseDto;
import com.ciudadania360.shared.application.service.IAService;
import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAClient iaClient;
    private final IAService iaService;

    // ---------------------------
    // 1️⃣ Chatbot
    // ---------------------------
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String reply = iaClient.chat(request.getConversationId(), request.getMessage());
        return new ChatResponse(reply, request.getConversationId(), reply);
    }

    // ---------------------------
    // 2️⃣ Procesamiento de texto
    // ---------------------------
    @PostMapping("/process")
    public String processText(@RequestParam String task, @RequestBody String text) {
        return iaClient.processText(text, task);
    }

    // ---------------------------
    // 3️⃣ Clasificación de documentos
    // ---------------------------
    @PostMapping("/classify")
    public String classifyDocument(@RequestParam String docType, @RequestBody String documentContent) {
        return iaClient.classifyDocument(documentContent, docType);
    }

    // ---------------------------
    // 4️⃣ Predicciones con modelos
    // ---------------------------
    @PostMapping("/predict")
    public ResponseEntity<ResponseDto> predict(@RequestBody RequestDto request) {
        String output = iaService.predict(request.prompt(), request.model());
        ResponseDto resp = new ResponseDto(output, request.model(), null);
        return ResponseEntity.ok(resp);
    }

    // ---------------------------
    // 5️⃣ Transcripción de audio
    // ---------------------------
    @PostMapping(value = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String transcribe(@RequestParam("file") MultipartFile file,
                             @RequestParam(defaultValue = "es") String language) throws Exception {
        byte[] content = file.getBytes();
        return iaClient.transcribe(content, language);
    }
}
