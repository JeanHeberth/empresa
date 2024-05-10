package com.br.empresa.api.service;

import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;


   private ModelMapper mapper = new ModelMapper();

    public List<FuncionarioResponseDto> buscarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioResponseDto.class))
                .collect(Collectors.toList());

    }

    public FuncionarioResponseDto buscarFuncionariosPorId(Long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (!optionalFuncionario.isPresent()) {
            throw new RuntimeException("Usuário não encontrado " + id);
        }
        return mapper.map(optionalFuncionario.get(), FuncionarioResponseDto.class);

    }
}
