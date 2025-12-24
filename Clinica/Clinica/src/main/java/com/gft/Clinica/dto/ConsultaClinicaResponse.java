package com.gft.Clinica.dto;

import java.time.LocalDateTime;

public record ConsultaClinicaResponse(
        Long id,
        Long pacienteId,
        String pacienteCpf,
        String pacienteNome,
        Long medicoId,
        String medicoNome,
        String especialidade,
        LocalDateTime dataHora
) {}