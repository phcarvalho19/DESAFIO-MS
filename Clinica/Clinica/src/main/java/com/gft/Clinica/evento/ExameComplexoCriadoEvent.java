package com.gft.Clinica.evento;

public record ExameComplexoCriadoEvent(
        Long consultaId,
        Long exameId
) {}