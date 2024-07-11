package com.br.empresa.api.controller;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamento")
public class OrcamentoController {

    @Autowired
    OrcamentoService orcamentoService;

    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDto>> buscarTodosOrcamento() {
        return ResponseEntity.ok(orcamentoService.buscarOrcamentos());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrcamentoResponseDto> buscarOrcamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(orcamentoService.buscarOrcamentoPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrcamentoResponseDto> cadastrarOrcamento(@RequestBody @Valid OrcamentoRequestDto dto) {
        return ResponseEntity.ok(orcamentoService.cadastrarOrcamento(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<OrcamentoResponseDto> atualizarOrcamento(@RequestBody OrcamentoRequestDto dto) {
        OrcamentoResponseDto orcamentoAtualizado = orcamentoService.atualizarOrcamento(dto);
        return ResponseEntity.ok(orcamentoAtualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }
}
