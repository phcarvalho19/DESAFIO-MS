package com.gft.Laboratorio.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exames")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pacienteCpf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "procedimento_id")
    private Procedimento procedimento;

    @Column(nullable = false)
    private LocalDateTime horario;

    public Exame() {}

    public Long getId() {
        return id;
    }

    public String getPacienteCpf() {
        return pacienteCpf;
    }

    public void setPacienteCpf(String pacienteCpf) {
        this.pacienteCpf = pacienteCpf;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
}