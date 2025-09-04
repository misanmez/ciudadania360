package com.ciudadania360.shared.application.exception;

import com.ciudadania360.shared.exception.GlobalExceptionHandler;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    @RestController
    static class DummyController {
        @PostMapping("/throw-validator")
        public void throwValidation() {
            throw new ValidationException("Error de validación");
        }
    }

    @Test
    void validationExceptionReturns400() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new DummyController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mvc.perform(post("/throw-validator")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Error de validación"));
    }
}
