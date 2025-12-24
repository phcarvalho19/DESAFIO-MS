package com.gft.Agendamento_de_exames_e_consultas.mapper;

import com.gft.Agendamento_de_exames_e_consultas.dto.ConsultaRequest;
import com.gft.Agendamento_de_exames_e_consultas.model.Consulta;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsultaMapper {
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static Consulta toEntity(ConsultaRequest r, Pacientes p){
        return new Consulta(null, p, r.especialidade(), LocalDateTime.parse(r.horario(), F));
    }
}
