package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MateriaRequestDto;
import com.projeto.StudyTracker.dto.MateriaResponseDto;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<MateriaResponseDto> findAll() {
        return materiaRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public MateriaResponseDto findById(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia não encontrada com id: " + id));
        return toResponseDto(materia);
    }

    @Transactional
    public MateriaResponseDto save(MateriaRequestDto dto) {
        Materia materia = new Materia();
        materia.setTitle(dto.title());
        materia.setDescricao(dto.descricao());
        Materia saved = materiaRepository.save(materia);
        return toResponseDto(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new RuntimeException("Materia não encontrada com id: " + id);
        }
        materiaRepository.deleteById(id);
    }

    private MateriaResponseDto toResponseDto(Materia materia) {
        return new MateriaResponseDto(materia.getId(), materia.getTitle(), materia.getDescricao());
    }
}
