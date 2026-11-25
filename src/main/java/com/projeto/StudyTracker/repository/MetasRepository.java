package com.projeto.StudyTracker.repository;

import com.projeto.StudyTracker.entity.Metas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MetasRepository extends JpaRepository<Metas, Long> {
}