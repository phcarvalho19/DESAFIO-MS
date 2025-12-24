package com.gft.Clinica.repository;


import com.gft.Clinica.model.Doenca;
import com.gft.Clinica.model.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {

    List<Doenca> findDistinctBySintomasIn(List<Sintoma> sintomas);
}