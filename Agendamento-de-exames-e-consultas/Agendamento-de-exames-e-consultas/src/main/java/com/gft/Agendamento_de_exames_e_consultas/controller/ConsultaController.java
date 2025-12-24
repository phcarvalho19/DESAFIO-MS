package com.gft.Agendamento_de_exames_e_consultas.controller;

import com.gft.Agendamento_de_exames_e_consultas.dto.ConsultaRequest;
import com.gft.Agendamento_de_exames_e_consultas.dto.ConsultaResponse;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ConflictException;
import com.gft.Agendamento_de_exames_e_consultas.repository.ConsultaRepository;
import com.gft.Agendamento_de_exames_e_consultas.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;
    private final ConsultaRepository repository;

    public ConsultaController(ConsultaService service, ConsultaRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponse> salvar(@Valid @RequestBody ConsultaRequest request) {
        var created = service.salvar(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<ConsultaResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultaRequest request) {

        ConsultaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }
}