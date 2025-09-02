package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CiudadanoFormHelperServiceTest {

    @Mock
    private IAService iaService;

    @InjectMocks
    private CiudadanoFormHelperService service;

    @Test
    void autocompleteFormDelegatesToIAService() {
        String partialForm = "nombre: Jua";
        String completion = "nombre: Juan Pérez";
        when(iaService.processText(partialForm, "form-autocomplete")).thenReturn(completion);

        String result = service.autocompleteForm(partialForm);

        assertThat(result).isEqualTo(completion);
        verify(iaService).processText(partialForm, "form-autocomplete");
    }
}
