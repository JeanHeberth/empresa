package com.br.empresa.api.controller;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping()
    public ResponseEntity<List<FuncionarioResponseDto>> buscarTodosFuncionarios() {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioPorId(id));
    }

    @DeleteMapping("/{id}")
    public void apagarFuncionario(@PathVariable Long id) {
        funcionarioService.apagarFuncionario(id);
    }

    @PostMapping()
    public ResponseEntity<FuncionarioResponseDto> cadastrarFuncionario(@RequestBody @Valid FuncionarioRequestDto dto) {
        funcionarioService.criptografarSenha(dto);
        return ResponseEntity.ok(funcionarioService.cadastrarFuncionario(dto));
    }

    @PutMapping()
    public ResponseEntity<FuncionarioResponseDto> atualizarFuncionario(@RequestBody FuncionarioRequestDto dto) {
        FuncionarioResponseDto funcionarioAtualizado = funcionarioService.atualizarFuncionario(dto.getId(), dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }


}
