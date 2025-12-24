package com.gft.Agendamento_de_exames_e_consultas.mapper;

import com.gft.Agendamento_de_exames_e_consultas.dto.PacienteDTO;
import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;

public class PacienteMapper {
    public static Pacientes toEntity(PacienteDTO d){ return new Pacientes(d.id(), d.nome(), d.idade(), d.cpf(), d.sexo());}
}