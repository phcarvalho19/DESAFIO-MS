package com.gft.Agendamento_de_exames_e_consultas.repository;

import com.gft.Agendamento_de_exames_e_consultas.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {


}