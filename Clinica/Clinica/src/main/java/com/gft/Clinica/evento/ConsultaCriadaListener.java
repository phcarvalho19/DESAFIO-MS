package com.gft.Clinica.evento;


import com.gft.Clinica.dto.ConsultaClinicaRequest;
import com.gft.Clinica.service.ConsultaClinicaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsultaCriadaListener {

    private final ConsultaClinicaService consultaClinicaService;

    public ConsultaCriadaListener(ConsultaClinicaService consultaClinicaService) {
        this.consultaClinicaService = consultaClinicaService;
    }

    @RabbitListener(queues = "consulta.clinica.queue")
    public void receberConsulta(ConsultaCriadaEvent event) {

        ConsultaClinicaRequest req =
                new ConsultaClinicaRequest(
                        event.pacienteId(),
                        event.pacienteCpf(),
                        event.pacienteNome(),
                        null,                  // medicoId (a clínica escolhe)
                        event.especialidade(),
                        event.dataHora()
                );
        consultaClinicaService.criar(req);
    }
}