package com.br.empresa.api.controller;


import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamento")
@CrossOrigin("http://localhost:4200")
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
    public void apagarDepartamento(@PathVariable Long id) {
        departamentoService.apagarDepartamento(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DepartamentoResponseDto> cadastrarDepartamento(@RequestBody @Valid DepartamentoRequestDto dto) {
        return ResponseEntity.created(null).body(departamentoService.cadastrarDepartamento(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> atualizarDepartamento(@Valid @RequestBody DepartamentoRequestDto dto) {
        DepartamentoResponseDto funcionarioAtualizado = departamentoService.atualizarDepartamento(dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }


}
