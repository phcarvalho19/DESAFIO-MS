package com.gft.Laboratorio.dto;

public record MarcarExameRequest(
        Long exameId,
        String pacienteCpf,
        Long procedimentoId,
        String horario
) {}