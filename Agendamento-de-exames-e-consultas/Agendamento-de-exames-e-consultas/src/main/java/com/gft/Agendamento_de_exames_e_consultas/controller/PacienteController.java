package com.gft.Agendamento_de_exames_e_consultas.controller;

import com.gft.Agendamento_de_exames_e_consultas.dto.PacienteDTO;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;
import com.gft.Agendamento_de_exames_e_consultas.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;
    public PacienteController(PacienteService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Pacientes> salvar(@Valid @RequestBody PacienteDTO dto){
        var saved = service.salvar(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<Pacientes> listar(){ return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Pacientes> buscar(@PathVariable Long id){
        var p = service.buscar(id);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pacientes> atualizar(@PathVariable Long id,
                                               @Valid @RequestBody Pacientes pacienteAtualizado) {
        Pacientes atualizado = service.atualizar(id, pacienteAtualizado);
        return ResponseEntity.ok(atualizado);
    }
}