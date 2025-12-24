package com.gft.Clinica.controller;


import com.gft.Clinica.dto.SintomaDTO;
import com.gft.Clinica.model.Sintoma;
import com.gft.Clinica.service.SintomaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinica/sintomas")
public class SintomaController {

    private final SintomaService service;

    public SintomaController(SintomaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Sintoma> criar(@RequestBody SintomaDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<SintomaDTO>> listar() {
        return ResponseEntity.ok(
                service.listar()
                        .stream()
                        .map(s -> new SintomaDTO(
                                s.getId(),
                                s.getNome(),
                                s.getPrioridade()
                        ))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SintomaDTO> buscar(@PathVariable Long id) {
        Sintoma s = service.buscar(id);
        return ResponseEntity.ok(
                new SintomaDTO(
                        s.getId(),
                        s.getNome(),
                        s.getPrioridade()
                )
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<Sintoma> atualizar(
            @PathVariable Long id,
            @RequestBody SintomaDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}