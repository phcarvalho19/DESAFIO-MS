package com.gft.Clinica.model;



import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doencas")
public class Doenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    // 🔹 Doença ↔ Sintomas (domínio da clínica)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "doenca_sintoma",
            joinColumns = @JoinColumn(name = "doenca_id"),
            inverseJoinColumns = @JoinColumn(name = "sintoma_id")
    )
    private List<Sintoma> sintomas = new ArrayList<>();

    // 🔥 IDs dos procedimentos do LABORATÓRIO
    // A clínica NÃO conhece Procedimento, só o ID
    @ElementCollection
    @CollectionTable(
            name = "doenca_procedimentos",
            joinColumns = @JoinColumn(name = "doenca_id")
    )
    @Column(name = "procedimento_id", nullable = false)
    private List<Long> procedimentosIds = new ArrayList<>();

    public Doenca() {}

    public Doenca(Long id, String nome, List<Sintoma> sintomas, List<Long> procedimentosIds) {
        this.id = id;
        this.nome = nome;
        this.sintomas = sintomas != null ? sintomas : new ArrayList<>();
        this.procedimentosIds = procedimentosIds != null ? procedimentosIds : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Sintoma> getSintomas() { return sintomas; }
    public void setSintomas(List<Sintoma> sintomas) {
        this.sintomas = sintomas != null ? sintomas : new ArrayList<>();
    }

    public List<Long> getProcedimentosIds() { return procedimentosIds; }
    public void setProcedimentosIds(List<Long> procedimentosIds) {
        this.procedimentosIds = procedimentosIds != null ? procedimentosIds : new ArrayList<>();
    }
}