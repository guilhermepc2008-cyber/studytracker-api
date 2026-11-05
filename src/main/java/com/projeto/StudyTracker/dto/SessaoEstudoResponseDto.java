package com.projeto.StudyTracker.dto;

import java.time.LocalDateTime;

public record SessaoEstudoResponseDto(
        Long id,
        Long materiaId,
        String materiaTitulo,
        LocalDateTime inicio,
        LocalDateTime fim,
        Integer quantidadeDeMinutos,
        String anotacao
) {
}
