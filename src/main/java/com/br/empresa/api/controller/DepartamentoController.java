package com.br.empresa.api.controller;


import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {


    @Autowired
    DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDto>> buscarTodosDepartamentos() {
        return ResponseEntity.ok(departamentoService.buscarDepartamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> buscarDepartamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.buscarDepartamentoPorId(id));
    }

    @DeleteMapping("/{id}")
    public void apagarFuncionario(@PathVariable Long id) {
        departamentoService.apagarFuncionario(id);
    }

    @PostMapping()
    public ResponseEntity<DepartamentoResponseDto> cadastrarDepartamentoo(@RequestBody @Valid DepartamentoRequestDto dto) {
        return ResponseEntity.ok(departamentoService.cadastrarDepartamento(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> atualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoRequestDto dto) {
        DepartamentoResponseDto funcionarioAtualizado = departamentoService.atualizarDepartamento(id, dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }
}
