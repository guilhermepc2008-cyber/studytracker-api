package com.projeto.StudyTracker.controller;

import com.projeto.StudyTracker.dto.AnotacaoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.service.SessaoEstudoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessao")
@Validated
public class SessaoEstudoController {

    private final SessaoEstudoService sessaoEstudoService;

    public SessaoEstudoController(SessaoEstudoService sessaoEstudoService) {
        this.sessaoEstudoService = sessaoEstudoService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciar(@RequestBody SessaoEstudoRequestDto dto) {
        if (sessaoEstudoService.verificaSessaoAberta()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoEstudoService.iniciar(dto));
    }

    @GetMapping()
    public ResponseEntity<List<SessaoEstudoResponseDto>> listarTodas() {
        return ResponseEntity.ok(sessaoEstudoService.listarTodas());
    }

    @GetMapping("/ativa")
    public ResponseEntity<SessaoEstudoResponseDto> buscarSessaoAtiva() {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.buscarSessaoAberta();
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

    @PatchMapping("/{id}/anotacao")
    public ResponseEntity<SessaoEstudoResponseDto> patchAnotacao(@PathVariable Long id,
                                                                 @RequestBody AnotacaoRequestDto anotacaoRequestDto) {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.patchAnotacao(id, anotacaoRequestDto);
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<SessaoEstudoResponseDto> patchFinalizar(@PathVariable Long id) {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.patchFinalizar(id);
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sessaoEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = {"de", "ate"})
    public ResponseEntity<List<SessaoEstudoResponseDto>> filtrarSessaoEstudo(@RequestParam
                                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime de,
                                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ate,
                                                                                 @RequestParam(required = false) Long materiaId
                                                                                 ) {

        if (materiaId == null) {
            return ResponseEntity.ok(sessaoEstudoService.verificaSessaoIntervalo(de, ate));
        }
        return ResponseEntity.ok(sessaoEstudoService.verificaSessaoIntervaloMateria(de, ate, materiaId));

    }

}
