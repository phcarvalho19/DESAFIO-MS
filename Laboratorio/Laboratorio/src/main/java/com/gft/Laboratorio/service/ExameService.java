package com.gft.Laboratorio.service;


import com.gft.Laboratorio.dto.MarcarExameRequest;
import com.gft.Laboratorio.dto.MarcarExameResponse;
import com.gft.Laboratorio.enums.Complexidade;
import com.gft.Laboratorio.exceptions.RegraNegocioException;
import com.gft.Laboratorio.model.Exame;
import com.gft.Laboratorio.model.Procedimento;
import com.gft.Laboratorio.repository.ExameRepository;
import com.gft.Laboratorio.repository.ProcedimentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExameService {

    private final ExameRepository exameRepo;
    private final ProcedimentoRepository procedimentoRepo;

    public ExameService(
            ExameRepository exameRepo,
            ProcedimentoRepository procedimentoRepo
    ) {
        this.exameRepo = exameRepo;
        this.procedimentoRepo = procedimentoRepo;
    }

    // =========================
    // CRUD DE EXAMES
    // =========================

    public Exame criar(Exame exame) {
        return exameRepo.save(exame);
    }

    public List<Exame> listar() {
        return exameRepo.findAll();
    }

    public Exame buscar(Long id) {
        return exameRepo.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Exame não encontrado"));
    }

    public Exame atualizar(Long id, Exame exame) {
        Exame existente = buscar(id);
        existente.setHorario(exame.getHorario());
        existente.setPacienteCpf(exame.getPacienteCpf());
        existente.setProcedimento(exame.getProcedimento());
        return exameRepo.save(existente);
    }

    public void deletar(Long id) {
        if (!exameRepo.existsById(id)) {
            throw new RegraNegocioException("Exame não encontrado");
        }
        exameRepo.deleteById(id);
    }

    // =========================
    // AÇÃO DE NEGÓCIO (REGRA)
    // =========================

    public MarcarExameResponse marcar(MarcarExameRequest req) {

        if (req.pacienteCpf() == null ||
                req.procedimentoId() == null ||
                req.horario() == null) {
            throw new RegraNegocioException("Campos obrigatórios ausentes");
        }

        Procedimento procedimento = procedimentoRepo.findById(req.procedimentoId())
                .orElseThrow(() ->
                        new RegraNegocioException("Procedimento não encontrado"));

        LocalDateTime horario = LocalDateTime.parse(req.horario());

        // 🔴 REGRA 1 — Alta complexidade exige consulta
        if (procedimento.getComplexidade() == Complexidade.ALTA &&
                req.exameId() == null) {
            throw new RegraNegocioException(
                    "Exames de alta complexidade exigem consulta prévia"
            );
        }

        // 🔴 REGRA 2 — Emergencial
        if (procedimento.isEmergencial()) {

            // remove exames COMUNS no mesmo horário
            exameRepo.deleteExamesComunsNoHorario(horario);

        } else {
            // 🔴 REGRA 3 — Exame comum não pode conflitar
            boolean conflito = req.exameId() == null
                    ? exameRepo.existsByHorario(horario)
                    : exameRepo.existsByHorarioAndIdNot(horario, req.exameId());

            if (conflito) {
                throw new RegraNegocioException("Horário ocupado");
            }
        }

        Exame exame = req.exameId() != null
                ? exameRepo.findById(req.exameId())
                .orElseThrow(() -> new RegraNegocioException("Exame não encontrado"))
                : new Exame();

        exame.setPacienteCpf(req.pacienteCpf());
        exame.setProcedimento(procedimento);
        exame.setHorario(horario);

        exameRepo.save(exame);

        return new MarcarExameResponse(
                exame.getId(),
                "Exame marcado com sucesso"
        );
    }
}