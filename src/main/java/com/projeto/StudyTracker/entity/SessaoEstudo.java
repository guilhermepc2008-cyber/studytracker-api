package com.projeto.StudyTracker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class SessaoEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Integer quantidadeDeMinutos;

    public SessaoEstudo() {
    }

    public SessaoEstudo(Long id, Materia materia, LocalDateTime inicio, LocalDateTime fim, Integer quantidadeDeMinutos) {
        this.id = id;
        this.materia = materia;
        this.inicio = inicio;
        this.fim = fim;
        this.quantidadeDeMinutos = quantidadeDeMinutos;
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