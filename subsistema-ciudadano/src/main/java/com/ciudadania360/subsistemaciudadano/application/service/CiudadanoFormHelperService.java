package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CiudadanoFormHelperService {

    private final IAService iaService;

    public String autocompleteForm(String partialFormData) {
        return iaService.processText(partialFormData, "form-autocomplete");
    }
}
