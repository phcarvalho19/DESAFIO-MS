package com.gft.Agendamento_de_exames_e_consultas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitacoes_exame")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pacientes paciente;

    private String descricao;

    public Exame() {
    }

    public Exame(Long id, Pacientes paciente, String descricao) {
        this.id = id;
        this.paciente = paciente;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}