package com.gft.Agendamento_de_exames_e_consultas.controller;

import com.gft.Agendamento_de_exames_e_consultas.dto.ExameRequest;
import com.gft.Agendamento_de_exames_e_consultas.dto.ExameResponse;
import com.gft.Agendamento_de_exames_e_consultas.service.ExameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadastro/exames")
public class ExameController {

    private final ExameService service;

    public ExameController(ExameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExameResponse> criar(
            @RequestBody @Valid ExameRequest request
    ) {
        return ResponseEntity.status(201).body(service.criar(request));
    }

    @GetMapping
    public List<ExameResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ExameRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}