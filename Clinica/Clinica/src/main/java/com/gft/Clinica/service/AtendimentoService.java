package com.gft.Clinica.service;

import com.gft.Clinica.dto.AtenderConsultaRequest;
import com.gft.Clinica.dto.AtenderConsultaResponse;
import com.gft.Clinica.evento.ExameComplexoSolicitadoEvent;
import com.gft.Clinica.exceptions.ResourceNotFoundException;
import com.gft.Clinica.model.ConsultaClinica;
import com.gft.Clinica.model.Doenca;
import com.gft.Clinica.model.Sintoma;
import com.gft.Clinica.repository.ConsultaClinicaRepository;
import com.gft.Clinica.repository.DoencaRepository;
import com.gft.Clinica.repository.SintomaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendimentoService {

    private final ConsultaClinicaRepository consultaRepo;
    private final SintomaRepository sintomaRepo;
    private final DoencaRepository doencaRepo;
    private final RabbitTemplate rabbit;

    public AtendimentoService(
            ConsultaClinicaRepository consultaRepo,
            SintomaRepository sintomaRepo,
            DoencaRepository doencaRepo,
            RabbitTemplate rabbit
    ) {
        this.consultaRepo = consultaRepo;
        this.sintomaRepo = sintomaRepo;
        this.doencaRepo = doencaRepo;
        this.rabbit = rabbit;
    }

    public AtenderConsultaResponse atender(AtenderConsultaRequest request) {

        ConsultaClinica consulta = consultaRepo.findById(request.consultaId())
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada"));

        List<Sintoma> sintomas = request.sintomas().stream()
                .map(nome ->
                        sintomaRepo.findByNomeIgnoreCase(nome)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("Sintoma não encontrado: " + nome))
                )
                .toList();

        List<Doenca> doencas = doencaRepo.findDistinctBySintomasIn(sintomas);

        if (doencas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma doença encontrada");
        }

        // 🔥 PARA CADA DOENÇA, SOLICITA EXAMES DE ALTA COMPLEXIDADE
        for (Doenca d : doencas) {
            for (Long procedimentoId : d.getProcedimentosIds()) {

                rabbit.convertAndSend(
                        "exame.exchange",
                        "exame.solicitado",
                        new ExameComplexoSolicitadoEvent(
                                consulta.getId(),
                                consulta.getPacienteCpf(),
                                procedimentoId
                        )
                );
            }
        }

        return new AtenderConsultaResponse(
                consulta.getId(),
                doencas.stream().map(Doenca::getNome).toList()
        );
    }
}