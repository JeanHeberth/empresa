package com.br.empresa.api.controller;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    OrcamentoService orcamentoService;

    @PostMapping
    public ResponseEntity<OrcamentoResponseDto> cadastrarOrcamento(@RequestBody @Valid OrcamentoRequestDto dto) {
        return ResponseEntity.ok(orcamentoService.cadastrarOrcamento(dto));
    }
}
