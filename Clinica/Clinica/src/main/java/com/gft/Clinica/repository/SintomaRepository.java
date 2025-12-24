package com.gft.Clinica.repository;


import com.gft.Clinica.model.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SintomaRepository extends JpaRepository<Sintoma, Long> {
    Optional<Sintoma> findByNomeIgnoreCase(String nome);
}