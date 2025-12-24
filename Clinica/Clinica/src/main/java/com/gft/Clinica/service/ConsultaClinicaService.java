package com.gft.Clinica.service;


import com.gft.Clinica.dto.ConsultaClinicaRequest;
import com.gft.Clinica.dto.ConsultaClinicaResponse;
import com.gft.Clinica.exceptions.ConflictException;
import com.gft.Clinica.exceptions.ResourceNotFoundException;
import com.gft.Clinica.model.ConsultaClinica;
import com.gft.Clinica.model.Medico;
import com.gft.Clinica.repository.ConsultaClinicaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaClinicaService {

    private final ConsultaClinicaRepository repo;
    private final MedicoService medicoService;

    public ConsultaClinicaService(ConsultaClinicaRepository repo, MedicoService medicoService) {
        this.repo = repo;
        this.medicoService = medicoService;
    }

    private LocalDateTime normalizar(LocalDateTime dt) {
        return dt.withSecond(0).withNano(0);
    }

    // CREATE
    public ConsultaClinicaResponse criar(ConsultaClinicaRequest req) {

        Medico medico = medicoService.buscarPorEspecialidade(req.especialidade());

        LocalDateTime dataHora = normalizar(LocalDateTime.parse(req.dataHora()));

        // REGRAS: Médico precisa ter 30 min livres
        var proximas = repo.findByMedicoIdAndDataHoraBetween(
                medico.getId(),
                dataHora.minusMinutes(30),
                dataHora.plusMinutes(30)
        );

        if (!proximas.isEmpty()) {
            throw new ConflictException("Médico já possui consulta próxima a este horário.");
        }

        ConsultaClinica c = new ConsultaClinica();
        c.setMedico(medico);
        c.setEspecialidade(req.especialidade());
        c.setPacienteId(req.pacienteId());
        c.setPacienteCpf(req.pacienteCpf());
        c.setPacienteNome(req.pacienteNome());
        c.setDataHora(dataHora);

        c = repo.save(c);

        return new ConsultaClinicaResponse(
                c.getId(),
                c.getPacienteId(),
                c.getPacienteCpf(),
                c.getPacienteNome(),
                medico.getId(),
                medico.getNome(),
                c.getEspecialidade(),
                c.getDataHora()
        );
    }

    // LISTAR TODAS
    public List<ConsultaClinicaResponse> listarTodas() {
        return repo.findAll()
                .stream()
                .map(c -> new ConsultaClinicaResponse(
                        c.getId(),
                        c.getPacienteId(),
                        c.getPacienteCpf(),
                        c.getPacienteNome(),
                        c.getMedico().getId(),
                        c.getMedico().getNome(),
                        c.getEspecialidade(),
                        c.getDataHora()
                ))
                .toList();
    }

    // BUSCAR 1
    public ConsultaClinicaResponse buscar(Long id) {
        ConsultaClinica c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        return new ConsultaClinicaResponse(
                c.getId(),
                c.getPacienteId(),
                c.getPacienteCpf(),
                c.getPacienteNome(),
                c.getMedico().getId(),
                c.getMedico().getNome(),
                c.getEspecialidade(),
                c.getDataHora()
        );
    }

    // UPDATE
    public ConsultaClinicaResponse atualizar(Long id, ConsultaClinicaRequest req) {

        ConsultaClinica existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        Medico medico = medicoService.buscarPorEspecialidade(req.especialidade());

        LocalDateTime nova = normalizar(LocalDateTime.parse(req.dataHora()));

        var proximas = repo.findByMedicoIdAndDataHoraBetween(
                medico.getId(),
                nova.minusMinutes(30),
                nova.plusMinutes(30)
        );

        final Long idExistente = existente.getId(); // 👈 FIX
        proximas.removeIf(c -> c.getId().equals(idExistente));

        if (!proximas.isEmpty()) {
            throw new ConflictException("Médico já possui consulta próxima a este horário.");
        }

        existente.setMedico(medico);
        existente.setEspecialidade(req.especialidade());
        existente.setPacienteCpf(req.pacienteCpf());
        existente.setPacienteNome(req.pacienteNome());
        existente.setPacienteId(req.pacienteId());
        existente.setDataHora(nova);

        existente = repo.save(existente);

        return new ConsultaClinicaResponse(
                existente.getId(),
                existente.getPacienteId(),
                existente.getPacienteCpf(),
                existente.getPacienteNome(),
                medico.getId(),
                medico.getNome(),
                existente.getEspecialidade(),
                existente.getDataHora()
        );
    }

    // DELETE
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Consulta não encontrada");
        }
        repo.deleteById(id);
    }
}