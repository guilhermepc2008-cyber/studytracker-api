package com.projeto.StudyTracker.dto;

public record ProgressoMetaDto(
    String materiaTitulo,
    Integer metaMinutos,
    Integer estudadoMinutos,
    Integer faltamMinutos,
    Double percentual,
    Boolean atingida
) {
}