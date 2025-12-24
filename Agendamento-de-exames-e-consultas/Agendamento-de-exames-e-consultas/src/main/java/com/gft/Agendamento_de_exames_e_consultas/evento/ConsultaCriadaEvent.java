package com.gft.Agendamento_de_exames_e_consultas.evento;

public record ConsultaCriadaEvent(
        Long pacienteId,
        String pacienteCpf,
        String pacienteNome,
        String especialidade,
        String dataHora
) {}