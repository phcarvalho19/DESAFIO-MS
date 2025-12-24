package com.gft.Clinica.service;


import com.gft.Clinica.dto.MedicoDTO;
import com.gft.Clinica.exceptions.ResourceNotFoundException;
import com.gft.Clinica.model.Medico;
import com.gft.Clinica.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repo;

    public MedicoService(MedicoRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public Medico criar(MedicoDTO dto) {
        Medico m = new Medico();
        m.setNome(dto.nome());
        m.setEspecialidade(dto.especialidade());
        return repo.save(m);
    }

    // READ - listar
    public List<Medico> listar() {
        return repo.findAll();
    }

    // READ - buscar por ID
    public Medico buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Médico não encontrado"));
    }

    // READ - por especialidade
    public Medico buscarPorEspecialidade(String especialidade) {
        return repo.findByEspecialidadeIgnoreCase(especialidade)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Nenhum médico encontrado para a especialidade " + especialidade));
    }

    // UPDATE
    public Medico atualizar(Long id, MedicoDTO dto) {
        Medico m = buscarPorId(id);
        m.setNome(dto.nome());
        m.setEspecialidade(dto.especialidade());
        return repo.save(m);
    }

    // DELETE
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Médico não encontrado");
        }
        repo.deleteById(id);
    }
}