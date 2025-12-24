package com.gft.Agendamento_de_exames_e_consultas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PacienteDTO(Long id,
                          @NotBlank String nome,
                          @NotBlank @Size(min = 11, max = 12, message = "CPF deve ter até 12 caracteres") String cpf,
                          Integer idade,
                          String sexo)
{}
