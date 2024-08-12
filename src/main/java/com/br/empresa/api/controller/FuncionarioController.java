package com.br.empresa.api.controller;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.dto.PessoaResponseDto;
import com.br.empresa.api.service.FuncionarioService;
import com.br.empresa.api.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    MatriculaService matriculaService;
//
//    @PostMapping("/autenticar")
//    public ResponseEntity autenticar(@RequestBody FuncionarioRequestDto usuarioDTO) {
//        try {
//            Funcionario usuarioAutenticado = funcionarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
//            return ResponseEntity.ok(usuarioAutenticado);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping()
    public ResponseEntity<List<FuncionarioResponseDto>> buscarTodosFuncionarios() {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarios());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioPorId(id));
    }


    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncionarioPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(funcionarioService.buscarFuncionarioPorMatricula(matricula));
    }

    @DeleteMapping("/{id}")
    public void apagarFuncionario(@PathVariable Long id) {
        funcionarioService.apagarFuncionario(id);
    }

    @PostMapping()
    public ResponseEntity<FuncionarioResponseDto> cadastrarFuncionario(@RequestBody @Valid FuncionarioRequestDto dto) {
        return ResponseEntity.ok(funcionarioService.cadastrarFuncionario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDto> atualizarFuncionario(@RequestBody FuncionarioRequestDto dto) {
        FuncionarioResponseDto funcionarioAtualizado = funcionarioService.atualizarFuncionario(dto.getId(), dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @GetMapping("cpf/{cpf}")
    public ResponseEntity<List<FuncionarioResponseDto>> buscarPessoasPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(funcionarioService.buscarPessoasPorCpf(cpf));
    }

    @GetMapping("/generate/matricula")
    public Map<String, String> generateMatricula(@RequestParam String cpf, @RequestParam String dataAdmissao) {
        LocalDate data = LocalDate.parse(dataAdmissao);
        String matricula = matriculaService.gerarMatricula(data, cpf);
        Map<String, String> response = new HashMap<>();
        response.put("matricula", matricula);
        return response;
    }
}
