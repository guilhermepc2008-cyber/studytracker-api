package com.projeto.StudyTracker.controller;

import com.projeto.StudyTracker.dto.MetaRequestDto;
import com.projeto.StudyTracker.dto.MetaResponseDto;
import com.projeto.StudyTracker.dto.ProgressoMetaDto;
import com.projeto.StudyTracker.service.MetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@Validated
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping
    public ResponseEntity<List<MetaResponseDto>> listarTodos() {
        List<MetaResponseDto> metas = metaService.buscarTodos();
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaResponseDto> buscarPorId(@PathVariable Long id) {
        MetaResponseDto meta = metaService.buscarPorId(id);
        if (meta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meta);
    }

    @GetMapping("/{id}/progresso")
    public ResponseEntity<ProgressoMetaDto> analisarProgresso(@PathVariable Long id) {
        ProgressoMetaDto progresso = metaService.analisarProgresso(id);
        return ResponseEntity.ok(progresso);
    }

    @PostMapping
    public ResponseEntity<MetaResponseDto> criarMeta(@RequestBody MetaRequestDto dto) {
        MetaResponseDto meta = metaService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(meta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDto> atualizarMeta(@PathVariable Long id,
                                                         @RequestBody MetaRequestDto dto) {
        MetaResponseDto metaAtualizada = metaService.atualizar(dto, id);
        if (metaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
