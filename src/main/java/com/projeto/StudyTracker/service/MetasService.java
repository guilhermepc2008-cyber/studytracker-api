package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.entity.Metas;
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

    public Metas converterParaEntidade(MetaRequestDto dto) {
        Metas metas = new Metas();
        metas.setQtdminuto(dto.minutosPorSemana());
        return metas;
    }

    public MetaResponseDto converterParaDto(Metas metas) {
        return new MetaResponseDto(
                metas.getId(),
                metas.getMateria().getId(),
                metas.getMateria().getTitulo(),
                metas.getQtdminuto()
        );
    }

    public MetaResponseDto salvar(MetaRequestDto dto) {
        Metas metas = converterParaEntidade(dto);
        metasRepository.save(metas);
        return converterParaDto(metas);
    }

    public void deletar(Long id) {
        if (!existePorId(id)) {
            return;
        }
        metasRepository.deleteById(id);
    }

    public MetaResponseDto atualizar(MetaRequestDto dto, Long id) {
        Optional<Metas> metasExistente = metasRepository.findById(id);
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
        Optional<Metas> metas = metasRepository.findById(id);
        return metas.map(this::converterParaDto).orElse(null);
    }

    public List<MetaResponseDto> buscarTodos() {
        return metasRepository.findAll().stream()
                .map(this::converterParaDto)
                .toList();
    }
}