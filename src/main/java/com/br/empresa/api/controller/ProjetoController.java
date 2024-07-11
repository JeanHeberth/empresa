package com.br.empresa.api.controller;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.dto.ProjetoRequestDto;
import com.br.empresa.api.dto.ProjetoResponseDto;
import com.br.empresa.api.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projeto")
public class ProjetoController {

    @Autowired
    ProjetoService projetoService;

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDto>> buscarTodosProjetos() {
        return ResponseEntity.ok(projetoService.buscarProjetos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDto> buscarProjetoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarProjetoPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseDto> cadastrarProjetos(@RequestBody @Valid ProjetoRequestDto dto) {
        return ResponseEntity.ok(projetoService.cadastrarProjeto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDto> atualizarProjetos(@RequestBody ProjetoRequestDto dto) {
        ProjetoResponseDto projetoAtualizado = projetoService.atualizarProjeto(dto);
        return ResponseEntity.ok(projetoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.ok().build();
    }
}
