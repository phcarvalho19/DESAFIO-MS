package com.gft.Agendamento_de_exames_e_consultas.service;



import com.gft.Agendamento_de_exames_e_consultas.dto.ExameRequest;
import com.gft.Agendamento_de_exames_e_consultas.dto.ExameResponse;
import com.gft.Agendamento_de_exames_e_consultas.evento.ExameSolicitadoEvent;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ResourceNotFoundException;
import com.gft.Agendamento_de_exames_e_consultas.model.Exame;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;
import com.gft.Agendamento_de_exames_e_consultas.repository.ExameRepository;
import com.gft.Agendamento_de_exames_e_consultas.repository.PacienteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExameService {

    private final ExameRepository repo;
    private final PacienteRepository pacienteRepo;
    private final RabbitTemplate rabbit;

    public ExameService(
            ExameRepository repo,
            PacienteRepository pacienteRepo,
            RabbitTemplate rabbit
    ) {
        this.repo = repo;
        this.pacienteRepo = pacienteRepo;
        this.rabbit = rabbit;
    }

    // CREATE
    public ExameResponse criar(ExameRequest request) {

        Pacientes paciente = pacienteRepo.findById(request.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        Exame s = new Exame();
        s.setPaciente(paciente);
        s.setDescricao(request.descricao());

        s = repo.save(s);

        // 🔥 EVENTO PARA CLÍNICA
        rabbit.convertAndSend(
                "exame.exchange",
                "exame.solicitado",
                new ExameSolicitadoEvent(
                        s.getId(),
                        paciente.getId(),
                        paciente.getCpf(),
                        paciente.getName(),
                        s.getDescricao()
                )
        );

        return toResponse(s);
    }

    // READ
    public ExameResponse buscar(Long id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada"));
    }

    public List<ExameResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    // UPDATE (somente descrição, se quiser permitir)
    public ExameResponse atualizar(Long id, ExameRequest request) {
        Exame s = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada"));

        s.setDescricao(request.descricao());

        return toResponse(repo.save(s));
    }

    // DELETE
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Solicitação não encontrada");
        }
        repo.deleteById(id);
    }

    private ExameResponse toResponse(Exame s) {
        return new ExameResponse(
                s.getId(),
                s.getPaciente().getId(),
                s.getDescricao()
        );
    }
}