package com.gft.Clinica.repository;
import com.gft.Clinica.model.ConsultaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaClinicaRepository extends JpaRepository<ConsultaClinica, Long> {

    boolean existsByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);

    Optional<ConsultaClinica> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);

    List<ConsultaClinica> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);

    List<ConsultaClinica> findByPacienteCpf(String cpf);

    // caso precise listar todas do medico num range amplo (usado no listarPorMedico)
    List<ConsultaClinica> findByMedicoId(Long medicoId);
}