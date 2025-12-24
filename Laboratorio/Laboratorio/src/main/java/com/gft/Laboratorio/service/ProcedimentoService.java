package com.gft.Laboratorio.service;

import com.gft.Laboratorio.dto.ProcedimentoDTO;
import com.gft.Laboratorio.model.Procedimento;
import com.gft.Laboratorio.repository.ProcedimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedimentoService {

    private final ProcedimentoRepository repo;

    public ProcedimentoService(ProcedimentoRepository repo) {
        this.repo = repo;
    }

    public Procedimento criar(ProcedimentoDTO dto) {
        Procedimento p = new Procedimento();
        p.setNome(dto.nome());
        p.setComplexidade(dto.complexidade());
        p.setEmergencial(dto.emergencial());
        return repo.save(p);
    }

    public List<Procedimento> listar() {
        return repo.findAll();
    }

    public Procedimento buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));
    }

    public Procedimento atualizar(Long id, ProcedimentoDTO dto) {
        Procedimento p = buscar(id);
        p.setNome(dto.nome());
        p.setComplexidade(dto.complexidade());
        p.setEmergencial(dto.emergencial());
        return repo.save(p);
    }

    public void deletar(Long id) {
        repo.delete(buscar(id));
    }
}