package com.gft.Clinica.service;

import com.gft.Clinica.dto.DoencaDTO;
import com.gft.Clinica.exceptions.ResourceNotFoundException;
import com.gft.Clinica.model.Doenca;
import com.gft.Clinica.model.Sintoma;
import com.gft.Clinica.repository.DoencaRepository;
import com.gft.Clinica.repository.SintomaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoencaService {

    private final DoencaRepository repo;
    private final SintomaRepository sintomaRepo;

    public DoencaService(
            DoencaRepository repo,
            SintomaRepository sintomaRepo
    ) {
        this.repo = repo;
        this.sintomaRepo = sintomaRepo;
    }

    // =========================
    // CREATE
    // =========================

    public Doenca criar(DoencaDTO dto) {

        List<Sintoma> sintomas = sintomaRepo.findAllById(dto.sintomasIds());
        if (sintomas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum sintoma válido encontrado");
        }

        Doenca d = new Doenca();
        d.setNome(dto.nome());
        d.setSintomas(sintomas);
        d.setProcedimentosIds(dto.procedimentosIds());

        return repo.save(d);
    }

    // =========================
    // READ
    // =========================

    public List<Doenca> listar() {
        return repo.findAll();
    }

    public Doenca buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doença não encontrada"));
    }

    // =========================
    // UPDATE
    // =========================

    public Doenca atualizar(Long id, DoencaDTO dto) {

        Doenca d = buscar(id);

        List<Sintoma> sintomas = sintomaRepo.findAllById(dto.sintomasIds());
        if (sintomas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum sintoma válido encontrado");
        }

        d.setNome(dto.nome());
        d.setSintomas(sintomas);
        d.setProcedimentosIds(dto.procedimentosIds());

        return repo.save(d);
    }

    // =========================
    // DELETE
    // =========================

    public void deletar(Long id) {
        Doenca d = buscar(id);
        repo.delete(d);
    }
}