package com.gft.Clinica.dto;

import java.util.List;

public record DoencaDTO(
        Long id,
        String nome,
        List<Long> sintomasIds,
        List<Long> procedimentosIds
) {}