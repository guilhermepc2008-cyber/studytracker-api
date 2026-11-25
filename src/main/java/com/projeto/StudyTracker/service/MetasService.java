package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.entity.Meta;
import com.projeto.StudyTracker.repository.MetasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetasService {

    private final MetasRepository metasRepository;

    public MetasService(MetasRepository metasRepository) {
        this.metasRepository = metasRepository;
    }

    public Meta converterParaEntidade(MetaRequestDto dto) {
        Meta meta = new Meta();
        meta.setQtdminuto(dto.minutosPorSemana());
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
        metasRepository.save(meta);
        return converterParaDto(meta);
    }

    public void deletar(Long id) {
        if (!existePorId(id)) {
            return;
        }
        metasRepository.deleteById(id);
    }

    public MetaResponseDto atualizar(MetaRequestDto dto, Long id) {
        Optional<Meta> metasExistente = metasRepository.findById(id);
        return metasExistente.map(m -> {
            m.setQtdminuto(dto.minutosPorSemana());
            metasRepository.save(m);
            return converterParaDto(m);
        }).orElse(null);
    }

    public boolean existePorId(Long id) {
        return metasRepository.existsById(id);
    }

    public MetaResponseDto buscarPorId(Long id) {
        Optional<Meta> metas = metasRepository.findById(id);
        return metas.map(this::converterParaDto).orElse(null);
    }

    public List<MetaResponseDto> buscarTodos() {
        return metasRepository.findAll().stream()
                .map(this::converterParaDto)
                .toList();
    }
}