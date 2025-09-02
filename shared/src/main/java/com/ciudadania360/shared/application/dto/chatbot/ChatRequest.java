package com.ciudadania360.shared.application.dto.chatbot;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @NotBlank
    private UUID conversationId;

    @NotBlank
    private String message;
}
