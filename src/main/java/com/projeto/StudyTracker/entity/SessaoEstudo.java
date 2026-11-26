package com.projeto.StudyTracker.entity;

import com.projeto.StudyTracker.dto.ProgressoMetaDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class SessaoEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Integer quantidadeDeMinutos;
    private String anotacao;

    public SessaoEstudo() {
    }

    public SessaoEstudo(Long id, Materia materia, LocalDateTime inicio, LocalDateTime fim, Integer quantidadeDeMinutos, String anotacao) {
        this.id = id;
        this.materia = materia;
        this.inicio = inicio;
        this.fim = fim;
        this.quantidadeDeMinutos = quantidadeDeMinutos;
        this.anotacao = anotacao;
    }

    public SessaoEstudo(Materia materia, String anotacao) {
        this.materia = materia;
        this.inicio = LocalDateTime.now();
        this.anotacao = anotacao;
    }

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
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

    public void finalizar() {
        this.fim = LocalDateTime.now();
        calcularQuantidadeDeMinutos();
    }

    public Integer getQuantidadeDeMinutos() {
        return quantidadeDeMinutos;
    }

    public void setQuantidadeDeMinutos(Integer quantidadeDeMinutos) {
        this.quantidadeDeMinutos = quantidadeDeMinutos;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }


}
