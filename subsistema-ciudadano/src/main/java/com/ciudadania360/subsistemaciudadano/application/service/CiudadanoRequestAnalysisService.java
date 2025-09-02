package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CiudadanoRequestAnalysisService {

    private final IAService iaService;

    public String classifyRequest(String requestContent) {
        return iaService.classifyDocument(requestContent, "request-type");
    }
}
