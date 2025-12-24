package com.gft.Laboratorio.repository;

import com.gft.Laboratorio.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    // =========================
    // CONFLITO DE HORÁRIO (EXAME COMUM)
    // =========================

    boolean existsByHorario(LocalDateTime horario);

    boolean existsByHorarioAndIdNot(
            LocalDateTime horario,
            Long id
    );

    // =========================
    // DUPLICIDADE (PACIENTE + PROCEDIMENTO + HORÁRIO)
    // =========================

    boolean existsByPacienteCpfAndProcedimentoIdAndHorario(
            String pacienteCpf,
            Long procedimentoId,
            LocalDateTime horario
    );

    // =========================
    // EMERGENCIAL — SOBRESCREVE COMUNS
    // =========================

    @Modifying
    @Query("""
        delete from Exame e
        where e.horario = :horario
          and e.procedimento.emergencial = false
    """)
    void deleteExamesComunsNoHorario(
            @Param("horario") LocalDateTime horario
    );
}