package com.gft.Agendamento_de_exames_e_consultas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Pacientes pacientes;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false)
    private LocalDateTime dataHora;
    //endregion

    //region Construtores
    public Consulta() {
    }

    public Consulta(Long id, Pacientes pacientes,
                    String especialidade, LocalDateTime dataHora) {
        this.id = id;
        this.pacientes = pacientes;
        this.especialidade = especialidade;
        this.dataHora = dataHora;
    }
    //endregion

    //region Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    //endregion
}