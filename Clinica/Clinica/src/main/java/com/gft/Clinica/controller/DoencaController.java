package com.gft.Clinica.controller;


import com.gft.Clinica.dto.DoencaDTO;
import com.gft.Clinica.model.Doenca;
import com.gft.Clinica.service.DoencaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinica/doencas")
public class DoencaController {

    private final DoencaService service;

    public DoencaController(DoencaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Doenca> criar(@RequestBody DoencaDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Doenca>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doenca> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doenca> atualizar(
            @PathVariable Long id,
            @RequestBody DoencaDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}