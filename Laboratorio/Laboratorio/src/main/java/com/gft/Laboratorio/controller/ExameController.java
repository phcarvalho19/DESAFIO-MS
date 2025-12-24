package com.gft.Laboratorio.controller;


import com.gft.Laboratorio.dto.MarcarExameRequest;
import com.gft.Laboratorio.dto.MarcarExameResponse;
import com.gft.Laboratorio.model.Exame;
import com.gft.Laboratorio.service.ExameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorio/exames")
public class ExameController {

    private final ExameService service;

    public ExameController(ExameService service) {
        this.service = service;
    }

    // 🔹 AÇÃO DE NEGÓCIO (mantém como está)
    @PostMapping("/marcar")
    public ResponseEntity<MarcarExameResponse> marcar(
            @RequestBody MarcarExameRequest request
    ) {
        return ResponseEntity.ok(service.marcar(request));
    }

    // 🔹 CRUD DE EXAMES

    @PostMapping
    public ResponseEntity<Exame> criar(@RequestBody Exame exame) {
        return ResponseEntity.ok(service.criar(exame));
    }

    @GetMapping
    public ResponseEntity<List<Exame>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exame> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exame> atualizar(
            @PathVariable Long id,
            @RequestBody Exame exame
    ) {
        return ResponseEntity.ok(service.atualizar(id, exame));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}