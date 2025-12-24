package com.gft.Agendamento_de_exames_e_consultas.dto;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        String pacienteNome,
        String especialidade,
        LocalDateTime dataHora
) {}