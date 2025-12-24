package com.gft.Laboratorio.model;


import com.gft.Laboratorio.enums.Complexidade;
import jakarta.persistence.*;

@Entity
@Table(name = "procedimentos")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Complexidade complexidade;

    @Column(nullable = false)
    private boolean emergencial;

    public Procedimento() {
    }

    public Procedimento(Long id, String nome, Complexidade complexidade, boolean emergencial) {
        this.id = id;
        this.nome = nome;
        this.complexidade = complexidade;
        this.emergencial = emergencial;
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

    public Complexidade getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(Complexidade complexidade) {
        this.complexidade = complexidade;
    }

    public boolean isEmergencial() {
        return emergencial;
    }

    public void setEmergencial(boolean emergencial) {
        this.emergencial = emergencial;
    }
}