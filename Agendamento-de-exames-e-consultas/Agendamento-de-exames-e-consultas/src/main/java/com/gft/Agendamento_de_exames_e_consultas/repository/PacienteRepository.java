package com.gft.Agendamento_de_exames_e_consultas.repository;

import com.gft.Agendamento_de_exames_e_consultas.model.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Pacientes, Long> {
    Optional<Pacientes> findByCpf(String cpf);
}
