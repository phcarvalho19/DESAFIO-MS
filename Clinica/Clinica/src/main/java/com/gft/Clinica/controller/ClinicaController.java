package com.gft.Clinica.controller;


import com.gft.Clinica.dto.ConsultaClinicaRequest;
import com.gft.Clinica.dto.ConsultaClinicaResponse;
import com.gft.Clinica.service.ConsultaClinicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinica/consultas")
public class ClinicaController {

    private final ConsultaClinicaService service;

    public ClinicaController(ConsultaClinicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConsultaClinicaResponse> criar(@RequestBody ConsultaClinicaRequest req) {
        return ResponseEntity.ok(service.criar(req));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaClinicaResponse>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaClinicaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaClinicaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ConsultaClinicaRequest req
    ) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}