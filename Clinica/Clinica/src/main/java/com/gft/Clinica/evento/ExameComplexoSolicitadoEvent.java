package com.gft.Clinica.evento;


public record ExameComplexoSolicitadoEvent(
        Long consultaId,
        String pacienteCpf,
        Long procedimentoId
) {}