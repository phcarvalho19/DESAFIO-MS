package com.gft.Agendamento_de_exames_e_consultas.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExameRequest(

        @NotNull(message = " é obrigatório")
        Long pacienteId,

        @NotNull(message = " é obrigatório")
        @Size(min = 1, message = "Descricao deve conter ao menos 1 caractere")
        String descricao
) {}