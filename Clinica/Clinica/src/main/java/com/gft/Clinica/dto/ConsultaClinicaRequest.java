package com.gft.Clinica.dto;

public record ConsultaClinicaRequest(
        Long pacienteId,
        String pacienteCpf,
        String pacienteNome,
        Long medicoId,         // preferencial: id do médico na clínica (pode vir nulo — aceita especialidade em fallback)
        String especialidade,
        String dataHora       // ISO_LOCAL_DATE_TIME
) {}