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
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    OrcamentoService orcamentoService;

    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDto>> buscarTodosOrcamento() {
        return ResponseEntity.ok(orcamentoService.buscarOrcamentos());
    }

    @PostMapping
    public ResponseEntity<OrcamentoResponseDto> cadastrarOrcamento(@RequestBody @Valid OrcamentoRequestDto dto) {
        return ResponseEntity.ok(orcamentoService.cadastrarOrcamento(dto));
    }
}
