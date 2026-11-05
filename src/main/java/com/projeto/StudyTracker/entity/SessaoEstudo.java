package com.projeto.StudyTracker.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity

public class SessaoEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessao_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private LocalDateTime inicio;
    private LocalDateTime fim;
    @Column(name = "quatidade_minutos")
    private Integer quantidadeDeMinutos;

    public SessaoEstudo() {
    }

    public SessaoEstudo(Long id, Materia materia, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.materia = materia;
        this.inicio = inicio;
        this.fim = fim;
    }

    @PrePersist
    @PreUpdate
    public void calcularQuantidadeDeMinutos() {
        if (this.inicio != null && this.fim != null) {
            this.quantidadeDeMinutos = (int) Duration.between(this.inicio, this.fim).toMinutes();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public Integer getQuantidadeDeMinutos() {
        return quantidadeDeMinutos;
    }

    public void setQuantidadeDeMinutos(Integer quantidadeDeMinutos) {
        this.quantidadeDeMinutos = quantidadeDeMinutos;
    }
}