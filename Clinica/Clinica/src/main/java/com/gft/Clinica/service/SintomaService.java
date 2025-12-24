package com.gft.Clinica.service;


import com.gft.Clinica.dto.SintomaDTO;
import com.gft.Clinica.exceptions.ResourceNotFoundException;
import com.gft.Clinica.model.Sintoma;
import com.gft.Clinica.repository.SintomaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SintomaService {

    private final SintomaRepository repository;

    public SintomaService(SintomaRepository repository) {
        this.repository = repository;
    }

    public Sintoma criar(SintomaDTO dto) {
        Sintoma sintoma = new Sintoma(
                dto.nome(),
                dto.prioridade()
        );
        return repository.save(sintoma);
    }

    public List<Sintoma> listar() {
        return repository.findAll();
    }

    public Sintoma buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sintoma não encontrado"));
    }

    public Sintoma atualizar(Long id, SintomaDTO dto) {
        Sintoma s = buscar(id);
        s.setNome(dto.nome());
        s.setPrioridade(dto.prioridade());
        return repository.save(s);
    }

    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Sintoma não encontrado");
        }


        repository.deleteById(id);
    }
}