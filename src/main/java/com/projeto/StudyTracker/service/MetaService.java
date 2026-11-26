package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.entity.Meta;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.repository.MateriaRepository;
import com.projeto.StudyTracker.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final MateriaRepository materiaRepository;

    public MetaService(MetaRepository metaRepository, MateriaRepository materiaRepository) {
        this.metaRepository = metaRepository;
        this.materiaRepository = materiaRepository;
    }

    public Meta converterParaEntidade(MetaRequestDto dto) {
        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + dto.materiaId()));

        Meta meta = new Meta();
        meta.setQtdminuto(dto.minutosPorSemana());
        meta.setMateria(materia);
        return meta;
    }

    public MetaResponseDto converterParaDto(Meta meta) {
        return new MetaResponseDto(
                meta.getId(),
                meta.getMateria().getId(),
                meta.getMateria().getTitulo(),
                meta.getQtdminuto()
        );
    }

    public MetaResponseDto salvar(MetaRequestDto dto) {
        Meta meta = converterParaEntidade(dto);
        metaRepository.save(meta);
        return converterParaDto(meta);
    }

    public void deletar(Long id) {
        if (!existePorId(id)) {
            return;
        }
        metaRepository.deleteById(id);
    }

    public MetaResponseDto atualizar(MetaRequestDto dto, Long id) {
        Optional<Meta> metaExistente = metaRepository.findById(id);
        return metaExistente.map(m -> {
            m.setQtdminuto(dto.minutosPorSemana());
            Materia materia = materiaRepository.findById(dto.materiaId())
                    .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + dto.materiaId()));
            m.setMateria(materia);
            metaRepository.save(m);
            return converterParaDto(m);
        }).orElse(null);
    }

    public boolean existePorId(Long id) {
        return metaRepository.existsById(id);
    }

    public MetaResponseDto buscarPorId(Long id) {
        Optional<Meta> meta = metaRepository.findById(id);
        return meta.map(this::converterParaDto).orElse(null);
    }

    public List<MetaResponseDto> buscarTodos() {
        return metaRepository.findAll().stream()
                .map(this::converterParaDto)
                .toList();
    }
}