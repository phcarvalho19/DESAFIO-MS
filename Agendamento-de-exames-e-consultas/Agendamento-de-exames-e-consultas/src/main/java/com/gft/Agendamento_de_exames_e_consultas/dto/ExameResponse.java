package com.gft.Agendamento_de_exames_e_consultas.dto;

public record ExameResponse(
        Long id,
        Long pacienteId,
        String status
) {}