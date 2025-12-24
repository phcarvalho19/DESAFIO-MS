package com.gft.Agendamento_de_exames_e_consultas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pacientes")
public class Pacientes {
    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer idade;

    @Column(nullable = false, length = 12, unique = true)
    private String cpf;

    private String sexo;

    //endregion
    //region Construtores
    public Pacientes() {
    }

    public Pacientes(Long id, String name, Integer idade, String cpf, String sexo) {
        this.id = id;
        this.name = name;
        this.idade = idade;
        this.cpf = cpf;
        this.sexo = sexo;
    }

    //endregion
    //region Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    //endregion
}
