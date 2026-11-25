package com.projeto.StudyTracker.entity;

import jakarta.persistence.*;

@Entity
public class Metas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meta_minutos",  nullable = false)
    private Integer qtdminuto;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id", nullable = false, unique = true)
    private Materia materia;



}
