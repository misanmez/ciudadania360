package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.RequestDto;
import com.ciudadania360.shared.application.service.IAService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class IAControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void generatePredict() throws Exception {
        IAService svc = mock(IAService.class);

        RequestDto request = new RequestDto("Hola IA", "gpt-test");
        when(svc.predict(request.prompt(), request.model())).thenReturn("Respuesta IA");

        IAController controller = new IAController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/ia/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Respuesta IA"))
                .andExpect(jsonPath("$.model").value("gpt-test"));

        verify(svc).predict(request.prompt(), request.model());
    }
}
