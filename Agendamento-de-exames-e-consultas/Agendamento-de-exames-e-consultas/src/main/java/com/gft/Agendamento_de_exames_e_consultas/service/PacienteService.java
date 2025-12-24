package com.gft.Agendamento_de_exames_e_consultas.service;


import com.gft.Agendamento_de_exames_e_consultas.dto.PacienteDTO;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ConflictException;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ResourceNotFoundException;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;
import com.gft.Agendamento_de_exames_e_consultas.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repo;

    public PacienteService(PacienteRepository repo) {
        this.repo = repo;
    }

    public Pacientes salvar(PacienteDTO dto) {

        if (dto.cpf().length() > 12) {
            throw new IllegalArgumentException("CPF não deve ser maior que 12 dígitos");
        }

        repo.findByCpf(dto.cpf())
                .ifPresent(existing -> {
                    throw new ConflictException("Já existe um paciente cadastrado com este CPF");
                });

        Pacientes p = new Pacientes(
                null,
                dto.nome(),
                dto.idade(),
                dto.cpf(),
                dto.sexo()
        );

        return repo.save(p);
    }

    public List<Pacientes> listar() {
        return repo.findAll();
    }

    public Pacientes buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }

    public Pacientes atualizar(Long id, Pacientes novosDados) {

        Pacientes existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        repo.findByCpf(novosDados.getCpf())
                .ifPresent(outro -> {
                    if (!outro.getId().equals(id)) {
                        throw new ConflictException("CPF já pertence a outro paciente");
                    }
                });

        existente.setName(novosDados.getName());
        existente.setCpf(novosDados.getCpf());
        existente.setIdade(novosDados.getIdade());
        existente.setSexo(novosDados.getSexo());

        return repo.save(existente);
    }

}