package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.RequestDto;
import com.ciudadania360.shared.application.dto.ResponseDto;
import com.ciudadania360.shared.application.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAService iaService;

    @PostMapping("/generate")
    public ResponseEntity<ResponseDto> predict(@RequestBody RequestDto request) {
        String output = iaService.predict(request.prompt(), request.model());
        ResponseDto resp = new ResponseDto(output, request.model(), null);
        return ResponseEntity.ok(resp);
    }
}
