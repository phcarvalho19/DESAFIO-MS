package com.gft.Clinica.dto;

import java.util.List;


public record AtenderConsultaResponse(
        Long consultaId,
        List<String> doencasProvaveis
) {}