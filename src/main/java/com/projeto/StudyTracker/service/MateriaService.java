package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MateriaRequestDto;
import com.projeto.StudyTracker.dto.MateriaResponseDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.entity.SessaoEstudo;
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

    public List<MateriaResponseDto> listarTodas() {
        return materiaRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public MateriaResponseDto buscarPorId(Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        return materia.map(this::converterParaDto).orElse(null);
    }

    public List<SessaoEstudoResponseDto> listarSessoesPorMateria(Long materiaId) {
        return materiaRepository.buscarSessoesPorMateria(materiaId).stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public MateriaResponseDto salvar(MateriaRequestDto dto) {
        Materia materia = converterParaEntidade(dto);
        materiaRepository.save(materia);
        return converterParaDto(materia);
    }

    public MateriaResponseDto atualizar(MateriaRequestDto dto, Long id) {
        return materiaRepository.findById(id).map(t -> {
                    t.setDescricao(dto.descricao());
                    t.setTitulo(dto.titulo());
                    materiaRepository.save(t);
                    return converterParaDto(t);
                }
        ).orElse(null);
    }

    public Materia converterParaEntidade(MateriaRequestDto dto) {
        Materia materia = new Materia();
        materia.setTitulo(dto.titulo());
        materia.setDescricao(dto.descricao());
        return materia;
    }

    public void deletar(Long id) {
        if (!materiaRepository.existsById(id)) {
            return;
        }
        materiaRepository.deleteById(id);
    }

    private MateriaResponseDto converterParaDto(Materia materia) {
        return new MateriaResponseDto(materia.getId(), materia.getTitulo(), materia.getDescricao());
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
