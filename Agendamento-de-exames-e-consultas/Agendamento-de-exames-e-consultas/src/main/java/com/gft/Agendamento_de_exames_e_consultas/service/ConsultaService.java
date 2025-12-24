package com.gft.Agendamento_de_exames_e_consultas.service;



import com.gft.Agendamento_de_exames_e_consultas.dto.ConsultaRequest;
import com.gft.Agendamento_de_exames_e_consultas.dto.ConsultaResponse;
import com.gft.Agendamento_de_exames_e_consultas.evento.ConsultaCriadaEvent;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ConflictException;
import com.gft.Agendamento_de_exames_e_consultas.exceptions.ResourceNotFoundException;
import com.gft.Agendamento_de_exames_e_consultas.model.Consulta;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;
import com.gft.Agendamento_de_exames_e_consultas.repository.ConsultaRepository;
import com.gft.Agendamento_de_exames_e_consultas.repository.PacienteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    private final RabbitTemplate rabbitTemplate;
    private final ConsultaRepository repository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(
            RabbitTemplate rabbitTemplate,
            ConsultaRepository repository,
            PacienteRepository pacienteRepository
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
        this.pacienteRepository = pacienteRepository;
    }

    // 🔧 Normaliza segundos e nanos
    private LocalDateTime normalizar(LocalDateTime dt) {
        return dt.withSecond(0).withNano(0);
    }

    // =========================
    // CREATE
    // =========================
    public ConsultaResponse salvar(ConsultaRequest request) {

        Pacientes paciente = pacienteRepository.findById(request.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        LocalDateTime dataHora = normalizar(LocalDateTime.parse(request.horario()));

        // 🔴 REGRA: 30 MINUTOS ENTRE CONSULTAS DA MESMA ESPECIALIDADE
        List<Consulta> conflitos = repository.findByEspecialidadeAndDataHoraBetween(
                request.especialidade(),
                dataHora.minusMinutes(30),
                dataHora.plusMinutes(30)
        );

        if (!conflitos.isEmpty()) {
            throw new ConflictException(
                    "Intervalo mínimo de 30 minutos entre consultas da mesma especialidade"
            );
        }

        // CRIA CONSULTA
        Consulta consulta = new Consulta();
        consulta.setPacientes(paciente);
        consulta.setEspecialidade(request.especialidade());
        consulta.setDataHora(dataHora);

        consulta = repository.save(consulta);

        // 🔥 PUBLICA EVENTO PARA A CLÍNICA
        rabbitTemplate.convertAndSend(
                "consulta.exchange",
                "consulta.criada",
                new ConsultaCriadaEvent(
                        paciente.getId(),
                        paciente.getCpf(),
                        paciente.getName(),
                        consulta.getEspecialidade(),
                        consulta.getDataHora().toString()
                )
        );

        return new ConsultaResponse(
                consulta.getId(),
                paciente.getName(),
                consulta.getEspecialidade(),
                consulta.getDataHora()
        );
    }

    // =========================
    // READ - LISTAR
    // =========================
    public List<ConsultaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(c -> new ConsultaResponse(
                        c.getId(),
                        c.getPacientes().getName(),
                        c.getEspecialidade(),
                        c.getDataHora()
                ))
                .toList();
    }

    // =========================
    // READ - BUSCAR
    // =========================
    public ConsultaResponse buscar(Long id) {
        Consulta c = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        return new ConsultaResponse(
                c.getId(),
                c.getPacientes().getName(),
                c.getEspecialidade(),
                c.getDataHora()
        );
    }

    // =========================
    // UPDATE
    // =========================
    public ConsultaResponse atualizar(Long id, ConsultaRequest request) {

        Consulta consulta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        Pacientes paciente = pacienteRepository.findById(request.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        LocalDateTime novaData = normalizar(LocalDateTime.parse(request.horario()));

        // 🔴 REGRA: 30 MINUTOS ENTRE CONSULTAS DA MESMA ESPECIALIDADE
        List<Consulta> conflitos = repository.findByEspecialidadeAndDataHoraBetweenAndIdNot(
                request.especialidade(),
                novaData.minusMinutes(30),
                novaData.plusMinutes(30),
                id
        );

        if (!conflitos.isEmpty()) {
            throw new ConflictException(
                    "Intervalo mínimo de 30 minutos entre consultas da mesma especialidade"
            );
        }

        consulta.setPacientes(paciente);
        consulta.setEspecialidade(request.especialidade());
        consulta.setDataHora(novaData);

        consulta = repository.save(consulta);

        return new ConsultaResponse(
                consulta.getId(),
                paciente.getName(),
                consulta.getEspecialidade(),
                consulta.getDataHora()
        );
    }

    // =========================
    // DELETE
    // =========================
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consulta não encontrada");
        }
        repository.deleteById(id);
    }
}