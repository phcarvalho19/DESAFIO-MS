package com.gft.Clinica.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas_clinica")
public class ConsultaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;
    private String pacienteCpf;
    private String pacienteNome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    // 🔥 EXAME DE ALTA COMPLEXIDADE (RETORNADO PELO LAB)
    private Long exameId;

    public ConsultaClinica() {
    }

    public ConsultaClinica(Long id,
                           Long pacienteId,
                           String pacienteCpf,
                           Medico medico,
                           String pacienteNome,
                           String especialidade,
                           Long exameId,
                           LocalDateTime dataHora) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.pacienteCpf = pacienteCpf;
        this.medico = medico;
        this.pacienteNome = pacienteNome;
        this.especialidade = especialidade;
        this.exameId = exameId;
        this.dataHora = dataHora;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getPacienteCpf() { return pacienteCpf; }
    public void setPacienteCpf(String pacienteCpf) { this.pacienteCpf = pacienteCpf; }

    public String getPacienteNome() { return pacienteNome; }
    public void setPacienteNome(String pacienteNome) { this.pacienteNome = pacienteNome; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Long getExameId() {
        return exameId;
    }

    public void setExameId(Long exameId) {
        this.exameId = exameId;
    }
}