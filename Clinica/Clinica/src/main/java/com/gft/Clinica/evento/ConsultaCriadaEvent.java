package com.gft.Clinica.evento;

public record ConsultaCriadaEvent(
        Long pacienteId,
        String pacienteCpf,
        String pacienteNome,
        String especialidade,
        String dataHora
) {}