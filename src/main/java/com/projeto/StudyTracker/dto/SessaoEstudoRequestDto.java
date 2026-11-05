package com.projeto.StudyTracker.dto;

import java.time.LocalDateTime;

public record SessaoEstudoRequestDto(
        Long materiaId,
        LocalDateTime inicio,
        LocalDateTime fim,
        String anotacao
) {
}
