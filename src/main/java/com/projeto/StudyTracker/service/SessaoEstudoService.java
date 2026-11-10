package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.AnotacaoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.entity.SessaoEstudo;
import com.projeto.StudyTracker.repository.MateriaRepository;
import com.projeto.StudyTracker.repository.SessaoEstudoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoEstudoService {

    private final SessaoEstudoRepository sessaoEstudoRepository;
    private final MateriaRepository materiaRepository;

    public SessaoEstudoService(SessaoEstudoRepository sessaoEstudoRepository,
                               MateriaRepository materiaRepository) {
        this.sessaoEstudoRepository = sessaoEstudoRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<SessaoEstudoResponseDto> findAll() {
        return sessaoEstudoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public SessaoEstudoResponseDto findById(Long id) {
        Optional<SessaoEstudo> sessaoEstudo = sessaoEstudoRepository.findById(id);
        return sessaoEstudo.map(this::toResponseDto).orElse(null);
    }

    public SessaoEstudoResponseDto patchanotacao(Long id,AnotacaoRequestDto anotacaoRequestDto) {
        return sessaoEstudoRepository.findById(id).map(t -> {
            t.setAnotacao(anotacaoRequestDto.anotacao());
            sessaoEstudoRepository.save(t);
            return toResponseDto(t);
        }).orElse(null);
    }


    public SessaoEstudoResponseDto save(SessaoEstudoRequestDto dto) {
        SessaoEstudo sessao = toEntity(dto);
        sessaoEstudoRepository.save(sessao);
        return toResponseDto(sessao);
    }

    public SessaoEstudoResponseDto post(SessaoEstudoRequestDto dto) {
        return save(dto);
    }

    public Boolean verificaSessaoAberta(){
        Optional<SessaoEstudo> sessao = sessaoEstudoRepository.findByFimIsNull();
        if(sessao.isPresent()){
            return true;
        }
        return false;
    }

    public SessaoEstudoResponseDto sessaoAberta(){
        Optional<SessaoEstudo> sessao = sessaoEstudoRepository.findByFimIsNull();
        if(sessao.isPresent()){
            sessao.get().setQuantidadeDeMinutos((int) Duration.between(sessao.get().getInicio(), LocalDateTime.now()).toMinutes());
            return toResponseDto(sessao.get());
        }
        return null;
    }



    public SessaoEstudoResponseDto update(SessaoEstudoRequestDto dto, Long id) {
        return sessaoEstudoRepository.findById(id).map(s -> {
            Materia materia = materiaRepository.findById(dto.materiaId())
                    .orElse(null);
            s.setMateria(materia);
            s.setInicio(dto.inicio());
            s.setFim(dto.fim());
            s.setAnotacao(dto.anotacao());
            sessaoEstudoRepository.save(s);
            return toResponseDto(s);
        }).orElse(null);
    }

    public SessaoEstudo toEntity(SessaoEstudoRequestDto dto) {
        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElse(null);

        SessaoEstudo sessao = new SessaoEstudo();
        sessao.setMateria(materia);
        sessao.setInicio(dto.inicio());
        sessao.setFim(dto.fim());
        sessao.setAnotacao(dto.anotacao());

        return sessao;
    }


    public void deleteById(Long id) {
        if (!sessaoEstudoRepository.existsById(id)) {
            return;
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
