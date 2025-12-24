package com.gft.Agendamento_de_exames_e_consultas.evento;

public record ExameSolicitadoEvent(
        Long solicitacaoId,
        Long pacienteId,
        String pacienteCpf,
        String pacienteNome,
        String descricao
) {}
