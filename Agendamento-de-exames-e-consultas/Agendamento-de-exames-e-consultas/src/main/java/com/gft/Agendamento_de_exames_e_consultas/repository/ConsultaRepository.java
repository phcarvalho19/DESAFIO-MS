package com.gft.Agendamento_de_exames_e_consultas.repository;

import com.gft.Agendamento_de_exames_e_consultas.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // 🔎 Verifica conflito de horário POR ESPECIALIDADE (regra dos 30 min)
    List<Consulta> findByEspecialidadeAndDataHoraBetween(
            String especialidade,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    // 🔎 Usado para atualização (ignora a própria consulta)
    List<Consulta> findByEspecialidadeAndDataHoraBetweenAndIdNot(
            String especialidade,
            LocalDateTime inicio,
            LocalDateTime fim,
            Long id
    );

    // 🔎 Caso queira buscar consulta exata (não obrigatório, mas útil)
    Optional<Consulta> findByEspecialidadeAndDataHora(
            String especialidade,
            LocalDateTime dataHora
    );
}