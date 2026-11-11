package com.projeto.StudyTracker.controller;

import com.projeto.StudyTracker.dto.AnotacaoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoRequestDto;
import com.projeto.StudyTracker.dto.SessaoEstudoResponseDto;
import com.projeto.StudyTracker.service.SessaoEstudoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> postsessao(@RequestBody SessaoEstudoRequestDto dto) {
        if (sessaoEstudoService.verificaSessaoAberta()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoEstudoService.inicar(dto));
    }

    @GetMapping()
    public ResponseEntity<List<SessaoEstudoResponseDto>> getSessaoEstudo() {
        return ResponseEntity.ok(sessaoEstudoService.findAll());
    }

    @GetMapping("/ativa")
    public ResponseEntity<SessaoEstudoResponseDto> getSessaoAtiva() {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.sessaoAberta();
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

    @PatchMapping("/{id}/anotacao")
    public ResponseEntity<SessaoEstudoResponseDto> patchAnotacao(@PathVariable Long id,
                                                                 @RequestBody AnotacaoRequestDto anotacaoRequestDto) {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.patchanotacao(id, anotacaoRequestDto);
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<SessaoEstudoResponseDto> patchFinalizar(@PathVariable Long id) {
        SessaoEstudoResponseDto sessao = sessaoEstudoService.patchfinalizar(id);
        if (sessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessao);
    }

}
