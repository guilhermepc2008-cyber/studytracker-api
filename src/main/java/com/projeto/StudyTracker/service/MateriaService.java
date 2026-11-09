package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MateriaRequestDto;
import com.projeto.StudyTracker.dto.MateriaResponseDto;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        Optional<Materia> materia  = materiaRepository.findById(id);
        return materia.map(this::toResponseDto).orElse(null);
    }

    public MateriaResponseDto save(MateriaRequestDto dto) {
        Materia materia = toEntity(dto);
        materiaRepository.save(materia);
        return toResponseDto(materia);
    }

    public Materia toEntity(MateriaRequestDto dto) {
        Materia materia = new Materia();
        materia.setTitle(dto.title());
        materia.setDescricao(dto.descricao());
        return materia;
    }

    public void deleteById(Long id) {
        if (!materiaRepository.existsById(id)) {
            return;
        }
        materiaRepository.deleteById(id);
    }

    private MateriaResponseDto toResponseDto(Materia materia) {
        return new MateriaResponseDto(materia.getId(), materia.getTitle(), materia.getDescricao());
    }
}
