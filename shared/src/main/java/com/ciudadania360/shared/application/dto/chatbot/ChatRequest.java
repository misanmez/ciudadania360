package com.ciudadania360.shared.application.dto.chatbot;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @NotBlank
    private String conversationId;

    @NotBlank
    private String message;
}
