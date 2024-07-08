package com.br.empresa.api.controller;

import com.br.empresa.api.dto.EnderecoRequestDto;
import com.br.empresa.api.dto.EnderecoResponseDto;
import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin("http://localhost:4200")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDto>> buscarTodosEnderecos() {
        return ResponseEntity.ok(enderecoService.buscarEnderecos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> buscarEnderecoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarEnderecoPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EnderecoResponseDto> cadastrarEndereco(@RequestBody @Valid EnderecoRequestDto dto) {
        return ResponseEntity.ok(enderecoService.cadastrarEndereco(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> atualizarEndereco(@RequestBody @Valid EnderecoRequestDto dto) {
        EnderecoResponseDto enderecoAtualizado = enderecoService.atualizarEndereco(dto);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void apagarEndereco(@PathVariable Long id) {
        enderecoService.deletarEndereco(id);
    }

}
