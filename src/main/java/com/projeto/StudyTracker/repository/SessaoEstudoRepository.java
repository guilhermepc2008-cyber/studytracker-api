package com.projeto.StudyTracker.repository;

import com.projeto.StudyTracker.entity.SessaoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {
}
