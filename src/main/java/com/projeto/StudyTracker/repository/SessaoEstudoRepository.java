package com.projeto.StudyTracker.repository;

import com.projeto.StudyTracker.entity.SessaoEstudo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {

    Optional<SessaoEstudo> findByFimIsNull();

    @Query("SELECT s FROM SessaoEstudo s WHERE s.inicio BETWEEN ?1 AND ?2 ORDER BY s.inicio ASC")
    List<SessaoEstudo> findByIntervalo(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT s FROM SessaoEstudo s WHERE s.materia.id = ?1 AND s.inicio BETWEEN ?2 AND ?3 ORDER BY s.inicio ASC")
    List<SessaoEstudo> findByMateriaAndIntervalo(Long materiaId, LocalDateTime inicio, LocalDateTime fim);

}
