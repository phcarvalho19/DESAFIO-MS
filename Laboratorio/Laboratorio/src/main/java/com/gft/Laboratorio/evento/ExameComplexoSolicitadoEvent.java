package com.gft.Laboratorio.evento;


public record ExameComplexoSolicitadoEvent(
        Long consultaId,
        String pacienteCpf,
        Long procedimentoId
) {}