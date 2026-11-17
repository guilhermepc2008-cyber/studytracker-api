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

    public List<SessaoEstudoResponseDto> listarTodas() {
        return sessaoEstudoRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public SessaoEstudoResponseDto buscarPorId(Long id) {
        Optional<SessaoEstudo> sessaoEstudo = sessaoEstudoRepository.findById(id);
        return sessaoEstudo.map(this::converterParaDto).orElse(null);
    }

    public SessaoEstudoResponseDto patchAnotacao(Long id, AnotacaoRequestDto anotacaoRequestDto) {
        return sessaoEstudoRepository.findById(id).map(t -> {
            t.setAnotacao(anotacaoRequestDto.anotacao());
            sessaoEstudoRepository.save(t);
            return converterParaDto(t);
        }).orElse(null);
    }

    public SessaoEstudoResponseDto iniciar(SessaoEstudoRequestDto dto) {
        SessaoEstudo sessao = converterParaEntidade(dto);
        sessaoEstudoRepository.save(sessao);
        return converterParaDto(sessao);
    }

    public Boolean verificaSessaoAberta() {
        Optional<SessaoEstudo> sessao = sessaoEstudoRepository.buscarPorFimNulo();
        if (sessao.isPresent()) {
            return true;
        }
        return false;
    }

    public List<SessaoEstudoResponseDto> verificaSessaoIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        return sessaoEstudoRepository.buscarPorIntervalo(inicio, fim)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public List<SessaoEstudoResponseDto> verificaSessaoIntervaloMateria(LocalDateTime inicio, LocalDateTime fim, Long materiaId) {
        return sessaoEstudoRepository.buscarPorMateriaEIntervalo(materiaId, inicio, fim)
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public SessaoEstudoResponseDto buscarSessaoAberta() {
        Optional<SessaoEstudo> sessao = sessaoEstudoRepository.buscarPorFimNulo();
        if (sessao.isPresent()) {
            sessao.get().setQuantidadeDeMinutos((int) Duration.between(sessao.get().getInicio(), LocalDateTime.now()).toMinutes());
            return converterParaDto(sessao.get());
        }
        return null;
    }

    public SessaoEstudo converterParaEntidade(SessaoEstudoRequestDto dto) {
        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElse(null);

        SessaoEstudo sessao = new SessaoEstudo(materia, dto.anotacao());
        return sessao;
    }

    public void deletar(Long id) {
        if (!sessaoEstudoRepository.existsById(id)) {
            return;
        }
        sessaoEstudoRepository.deleteById(id);
    }

    public SessaoEstudoResponseDto patchFinalizar(Long id) {
        return sessaoEstudoRepository.findById(id).map(s -> {
                    s.finalizar();
                    sessaoEstudoRepository.save(s);
                    return converterParaDto(s);
                }
        ).orElse(null);
    }

    private SessaoEstudoResponseDto converterParaDto(SessaoEstudo sessao) {
        return new SessaoEstudoResponseDto(
                sessao.getId(),
                sessao.getMateria().getId(),
                sessao.getMateria().getTitulo(),
                sessao.getInicio(),
                sessao.getFim(),
                sessao.getQuantidadeDeMinutos(),
                sessao.getAnotacao()
        );
    }
}
