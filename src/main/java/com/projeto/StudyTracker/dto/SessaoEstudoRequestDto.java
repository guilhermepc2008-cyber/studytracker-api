package com.projeto.StudyTracker.dto;

import java.time.LocalDateTime;

public record SessaoEstudoRequestDto(
        Long materiaId,
        String anotacao
) {
}
