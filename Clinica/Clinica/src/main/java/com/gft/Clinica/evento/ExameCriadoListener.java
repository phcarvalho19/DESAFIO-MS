package com.gft.Clinica.evento;

import com.gft.Clinica.model.ConsultaClinica;
import com.gft.Clinica.repository.ConsultaClinicaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ExameCriadoListener {

    private final ConsultaClinicaRepository consultaRepo;

    public ExameCriadoListener(ConsultaClinicaRepository consultaRepo) {
        this.consultaRepo = consultaRepo;
    }

    @RabbitListener(queues = "exame.criado.clinica.queue")
    public void receber(ExameComplexoCriadoEvent event) {

        ConsultaClinica consulta = consultaRepo.findById(event.consultaId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setExameId(event.exameId());
        consultaRepo.save(consulta);
    }
}