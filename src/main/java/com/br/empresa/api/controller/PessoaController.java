package com.br.empresa.api.controller;

import com.br.empresa.api.dto.PessoaRequestDto;
import com.br.empresa.api.dto.PessoaResponseDto;
import com.br.empresa.api.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pessoa")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping()
    public ResponseEntity<List<PessoaResponseDto>> buscarTodasPessoas() {
        return ResponseEntity.ok(pessoaService.buscarPessoas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> buscarPessoaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.buscarPessoaPorId(id));
    }

    @DeleteMapping("/{id}")
    public void apagarPessoa(@PathVariable Long id) {
        pessoaService.apagarPessoa(id);
    }

    @PostMapping()
    public ResponseEntity<PessoaResponseDto> cadastrarPessoa(@RequestBody @Valid PessoaRequestDto dto) {
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> atualizarPessoa(@RequestBody PessoaRequestDto dto) {
        PessoaResponseDto pessoaResponseDto = pessoaService.atualizarPessoa(dto.getId(), dto);
        return ResponseEntity.ok(pessoaResponseDto);
    }
}
