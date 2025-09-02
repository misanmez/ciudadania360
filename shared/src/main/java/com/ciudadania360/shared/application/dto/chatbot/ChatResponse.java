package com.ciudadania360.shared.application.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String responseText;
    private UUID conversationId;
    private String raw;
}
