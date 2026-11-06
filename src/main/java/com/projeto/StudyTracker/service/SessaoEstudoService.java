package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.SessaoEstudoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.entity.SessaoEstudo;
import com.projeto.StudyTracker.repository.MateriaRepository;
import com.projeto.StudyTracker.repository.SessaoEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessaoEstudoService {

    @Autowired
    private  SessaoEstudoRepository sessaoEstudoRepository;
    private  MateriaRepository materiaRepository;

    public List<SessaoEstudoResponseDto> findAll() {
        return sessaoEstudoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public SessaoEstudoResponseDto findById(Long id) {
        SessaoEstudo sessao = sessaoEstudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessao de estudo não encontrada com id: " + id));
        return toResponseDto(sessao);
    }


    public SessaoEstudoResponseDto save(SessaoEstudoRequestDto dto) {
        SessaoEstudo sessao = toEntity(dto);
        SessaoEstudo saved = sessaoEstudoRepository.save(sessao);
        return toResponseDto(saved);
    }

    public SessaoEstudo toEntity(SessaoEstudoRequestDto dto) {
        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new RuntimeException("Materia não encontrada com id: " + dto.materiaId()));

        SessaoEstudo sessao = new SessaoEstudo();
        sessao.setMateria(materia);
        sessao.setInicio(dto.inicio());
        sessao.setFim(dto.fim());
        sessao.setAnotacao(dto.anotacao());

        return sessao;
    }


    public void deleteById(Long id) {
        if (!sessaoEstudoRepository.existsById(id)) {
            throw new RuntimeException("Sessao de estudo não encontrada com id: " + id);
        }
        sessaoEstudoRepository.deleteById(id);
    }

    private SessaoEstudoResponseDto toResponseDto(SessaoEstudo sessao) {
        return new SessaoEstudoResponseDto(
                sessao.getId(),
                sessao.getMateria().getId(),
                sessao.getMateria().getTitle(),
                sessao.getInicio(),
                sessao.getFim(),
                sessao.getQuantidadeDeMinutos(),
                sessao.getAnotacao()
        );
    }
}
