package com.gft.Agendamento_de_exames_e_consultas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsultaRequest(
        @NotNull(message = " é obrigatório") Long pacienteId,
        @NotNull(message = " é obrigatório") @Size(min = 1) String horario, // ISO_LOCAL_DATE_TIME
        @NotNull(message = " é obrigatória") String especialidade,
        String descricao
) {}