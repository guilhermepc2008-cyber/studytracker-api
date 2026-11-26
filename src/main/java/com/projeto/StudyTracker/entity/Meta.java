package com.projeto.StudyTracker.entity;

import jakarta.persistence.*;

@Entity
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meta_minutos",  nullable = false)
    private Integer qtdminuto;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id", nullable = false, unique = true)
    private Materia materia;


    public Meta(Materia materia) {
        this.qtdminuto = 0;
        this.materia = materia;
    }

    public Meta() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtdminuto() {
        return qtdminuto;
    }

    public void setQtdminuto(Integer qtdminuto) {
        this.qtdminuto = qtdminuto;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
