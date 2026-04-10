package com.br.empresa.api.controller;

import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departamento")
@CrossOrigin("http://localhost:4200")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public ResponseEntity<Page<DepartamentoResponseDto>> buscarTodosDepartamentos(Pageable pageable) {
        return ResponseEntity.ok(departamentoService.buscarDepartamentos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> buscarDepartamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.buscarDepartamentoPorId(id));
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDto> cadastrarDepartamento(@Valid @RequestBody DepartamentoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoService.cadastrarDepartamento(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDto> atualizarDepartamento(
            @PathVariable Long id,
            @Valid @RequestBody DepartamentoRequestDto dto) {

        return ResponseEntity.ok(departamentoService.atualizarDepartamento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarDepartamento(@PathVariable Long id) {
        departamentoService.apagarDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}