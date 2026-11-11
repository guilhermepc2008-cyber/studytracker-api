package com.projeto.StudyTracker.repository;

import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.entity.SessaoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("SELECT s FROM SessaoEstudo s WHERE s.materia.id = ?1 ORDER BY s.inicio ASC")
    List<SessaoEstudo> Sessaomateria(Long materiaId);

}
