package com.projeto.StudyTracker.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materia_id")
    private Long id;
    private String title;
    private String descricao;
    @OneToMany(mappedBy = "sessoes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SessaoEstudo> sessoes;

    public Materia() {
    }

    public Materia(Long id, String title, String descricao) {
        this.id = id;
        this.title = title;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}