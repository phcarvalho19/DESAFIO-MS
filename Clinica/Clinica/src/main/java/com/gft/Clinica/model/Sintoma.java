package com.gft.Clinica.model;


import com.gft.Clinica.enums.Prioridade;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sintomas")
public class Sintoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;


    public Sintoma() {
    }

    public Sintoma(String nome, Prioridade prioridade) {
        this.nome = nome;
        this.prioridade = prioridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

}