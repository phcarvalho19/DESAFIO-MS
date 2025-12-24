package com.gft.Laboratorio.dto;

import com.gft.Laboratorio.enums.Complexidade;

public record ProcedimentoDTO(
        Long id,
        String nome,
        Complexidade complexidade,
        boolean emergencial)
{}
