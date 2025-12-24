package com.gft.Clinica.controller;

import com.gft.Clinica.dto.AtenderConsultaRequest;
import com.gft.Clinica.dto.AtenderConsultaResponse;
import com.gft.Clinica.service.AtendimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinica")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @PostMapping("/atender")
    public ResponseEntity<AtenderConsultaResponse> atender(
            @RequestBody AtenderConsultaRequest request
    ) {
        return ResponseEntity.ok(service.atender(request));
    }
}