package com.projeto.StudyTracker.dto;

public record ProgressoMetaDto(
    String materiaTitulo,
    Long metaMinutos,
    Long estudadoMinutos,
    Long faltamMinutos,
    Double percentual,
    Boolean atingida
) {
}