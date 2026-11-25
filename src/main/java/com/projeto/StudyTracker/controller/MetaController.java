package com.projeto.StudyTracker.controller;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.service.MetasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@Validated
public class MetaController {

    private final MetasService metasService;

    public MetaController(MetasService metasService) {
        this.metasService = metasService;
    }

    @GetMapping
    public ResponseEntity<List<MetaResponseDto>> listarTodos() {
        List<MetaResponseDto> metas = metasService.buscarTodos();
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaResponseDto> buscarPorId(@PathVariable Long id) {
        MetaResponseDto meta = metasService.buscarPorId(id);
        if (meta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meta);
    }

    @PostMapping
    public ResponseEntity<MetaResponseDto> criarMeta(@RequestBody MetaRequestDto dto) {
        MetaResponseDto meta = metasService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(meta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDto> atualizarMeta(@PathVariable Long id,
                                                     @RequestBody MetaRequestDto dto) {
        MetaResponseDto metaAtualizada = metasService.atualizar(dto, id);
        if (metaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}