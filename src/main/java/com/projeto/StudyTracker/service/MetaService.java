package com.projeto.StudyTracker.service;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.dto.ProgressoMetaDto;
import com.projeto.StudyTracker.entity.Meta;
import com.projeto.StudyTracker.entity.Materia;
import com.projeto.StudyTracker.repository.MateriaRepository;
import com.projeto.StudyTracker.repository.MetaRepository;
import com.projeto.StudyTracker.repository.SessaoEstudoRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final MateriaRepository materiaRepository;
    private final SessaoEstudoRepository sessaoEstudoRepository;

    public MetaService(MetaRepository metaRepository, MateriaRepository materiaRepository, SessaoEstudoRepository sessaoEstudoRepository) {
        this.metaRepository = metaRepository;
        this.materiaRepository = materiaRepository;
        this.sessaoEstudoRepository = sessaoEstudoRepository;
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

    public ProgressoMetaDto analisarProgresso(Long materiaId) {
        LocalDate hoje = LocalDate.now();
        LocalDate segunda = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime inicio = segunda.atStartOfDay();
        LocalDateTime fim = segunda.plusWeeks(1).atStartOfDay();

        Long minutostotais = sessaoEstudoRepository.somarMinutos(materiaId, inicio, fim);

        Meta meta = metaRepository.findByMateriaId(materiaId).get();


        Long metaMinutos = meta.getQtdminuto();
        Long faltamMinutos = Math.max(0, metaMinutos - minutostotais);
        Double percentual = (minutostotais * 100.0) / metaMinutos;
        Boolean atingida = minutostotais >= metaMinutos;

        return new ProgressoMetaDto(
                meta.getMateria().getTitulo(),
                metaMinutos,
                minutostotais,
                faltamMinutos,
                percentual,
                atingida
        );
    }
}
