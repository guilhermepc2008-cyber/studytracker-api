package com.projeto.StudyTracker.controller;

import com.projeto.StudyTracker.dto.MateriaRequestDto;
import com.projeto.StudyTracker.dto.MateriaResponseDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
@Validated
public class MateriasController {

    private final MateriaService materiaService;

    public MateriasController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping
    public ResponseEntity<MateriaResponseDto> postsessao(@RequestBody MateriaRequestDto dto) {
        MateriaResponseDto materia = materiaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(materia);
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponseDto>> listarTodas() {
        List<MateriaResponseDto> materias = materiaService.findAll();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDto> buscarPorId(@PathVariable Long id) {
        MateriaResponseDto materia = materiaService.findById(id);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/{id}/sessoes")
    public ResponseEntity<List<SessaoEstudoResponseDto>> listarSessoesPorMateria(@PathVariable Long id) {
        List<SessaoEstudoResponseDto> sessoes = materiaService.listarSessoesPorMateria(id);
        return ResponseEntity.ok(sessoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDto> atualizar(@PathVariable Long id,
                                                        @RequestBody MateriaRequestDto dto) {
        MateriaResponseDto materiaAtualizada = materiaService.update(dto, id);
        if (materiaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materiaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        materiaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
