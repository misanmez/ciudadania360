package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoFormHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ciudadano/form")
@RequiredArgsConstructor
public class CiudadanoFormHelperController {

    private final CiudadanoFormHelperService service;

    @PostMapping("/autocomplete")
    public String autocomplete(@RequestParam String partialForm) {
        return service.autocompleteForm(partialForm);
    }
}
