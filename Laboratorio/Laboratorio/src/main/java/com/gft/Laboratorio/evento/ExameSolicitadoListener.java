package com.gft.Laboratorio.evento;

import com.gft.Laboratorio.enums.Complexidade;
import com.gft.Laboratorio.model.Exame;
import com.gft.Laboratorio.model.Procedimento;
import com.gft.Laboratorio.repository.ExameRepository;
import com.gft.Laboratorio.repository.ProcedimentoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExameSolicitadoListener {

    private final ProcedimentoRepository procedimentoRepo;
    private final ExameRepository exameRepo;
    private final RabbitTemplate rabbit;

    public ExameSolicitadoListener(
            ProcedimentoRepository procedimentoRepo,
            ExameRepository exameRepo,
            RabbitTemplate rabbit
    ) {
        this.procedimentoRepo = procedimentoRepo;
        this.exameRepo = exameRepo;
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = "exame.solicitado.queue")
    public void receber(ExameComplexoSolicitadoEvent event) {

        Procedimento procedimento = procedimentoRepo.findById(event.procedimentoId())
                .orElseThrow(() ->
                        new RuntimeException("Procedimento não encontrado"));

        // 🔥 DECISÃO DE ALTA COMPLEXIDADE É DO LABORATÓRIO
        if (procedimento.getComplexidade() == Complexidade.ALTA) {

            Exame exame = new Exame();
            exame.setPacienteCpf(event.pacienteCpf());
            exame.setProcedimento(procedimento);

            exame = exameRepo.save(exame);

            // 🔁 RESPONDE PARA A CLÍNICA
            rabbit.convertAndSend(
                    "exame.exchange",
                    "exame.criado",
                    new ExameComplexoCriadoEvent(
                            event.consultaId(),
                            exame.getId()
                    )
            );
        }
    }
}