package com.br.empresa.api.controller;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping()
    public ResponseEntity<List<FuncionarioResponseDto>> buscarTodosFuncionarios(){
        return ResponseEntity.ok(funcionarioService.buscarFuncionarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable Long id){
        return ResponseEntity.ok(funcionarioService.buscarFuncionariosPorId(id));
    }

    @DeleteMapping("/{id}")
    public void apagarFuncionario(Long id){
//       funcionarioService.apagarFuncionario(id);
    }

    @PostMapping()
    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto dto){
        return null;
    }
    @PutMapping()
    public FuncionarioResponseDto atualizarFuncionario(FuncionarioRequestDto dto){
        return null;
    }

}
