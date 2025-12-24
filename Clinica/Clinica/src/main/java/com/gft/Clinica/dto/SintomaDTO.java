package com.gft.Clinica.dto;


import com.gft.Clinica.enums.Prioridade;

public record SintomaDTO(
        Long id,
        String nome,
        Prioridade prioridade
) {}