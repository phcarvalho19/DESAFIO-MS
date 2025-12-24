package com.gft.Clinica.dto;

import java.util.List;

public record AtenderConsultaRequest(
        Long consultaId,
        List<String> sintomas
) {}