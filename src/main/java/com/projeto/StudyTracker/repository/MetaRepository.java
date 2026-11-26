package com.projeto.StudyTracker.repository;

import com.projeto.StudyTracker.entity.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    Optional<Meta> findByMateriaId(Long materiaId);
}
