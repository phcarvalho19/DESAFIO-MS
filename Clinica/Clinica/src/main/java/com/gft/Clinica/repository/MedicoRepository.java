package com.gft.Clinica.repository;



import com.gft.Clinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByNomeAndEspecialidade(String nome, String especialidade);

    // busca todos da mesma especialidade (ignora case)
    List<Medico> findByEspecialidadeIgnoreCase(String especialidade);
}