package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.ia.client.IAClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IAServiceTest {

    @Mock
    private IAClient iaClient;

    @InjectMocks
    private IAService iaService;

    @Test
    void predictDelegatesToClient() {
        String prompt = "Hola IA";
        String model = "gpt-test";
        Mockito.when(iaClient.predict(prompt, model)).thenReturn("Respuesta IA");

        String result = iaService.predict(prompt, model);

        Assertions.assertThat(result).isEqualTo("Respuesta IA");
        Mockito.verify(iaClient).predict(prompt, model);
    }

    @Test
    void processTextDelegatesToClient() {
        String text = "Texto a procesar";
        String task = "analizar";
        Mockito.when(iaClient.processText(text, task)).thenReturn("Texto procesado");

        String result = iaService.processText(text, task);

        Assertions.assertThat(result).isEqualTo("Texto procesado");
        Mockito.verify(iaClient).processText(text, task);
    }

    @Test
    void classifyDocumentDelegatesToClient() {
        String content = "Contenido del documento";
        String docType = "formulario";
        Mockito.when(iaClient.classifyDocument(content, docType)).thenReturn("Clasificación correcta");

        String result = iaService.classifyDocument(content, docType);

        Assertions.assertThat(result).isEqualTo("Clasificación correcta");
        Mockito.verify(iaClient).classifyDocument(content, docType);
    }

    @Test
    void transcribeDelegatesToClient() {
        byte[] audio = "audio".getBytes();
        String lang = "es";
        Mockito.when(iaClient.transcribe(audio, lang)).thenReturn("Transcripción correcta");

        String result = iaService.transcribe(audio, lang);

        Assertions.assertThat(result).isEqualTo("Transcripción correcta");
        Mockito.verify(iaClient).transcribe(audio, lang);
    }
}
