package com.br.empresa.api.service;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();


    private ModelMapper mapper = new ModelMapper();

    public List<FuncionarioResponseDto> buscarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioResponseDto.class))
                .collect(Collectors.toList());

    }

    public FuncionarioResponseDto buscarFuncionarioPorId(Long id) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return new FuncionarioResponseDto(funcionarioExistente);

    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto dto) {

        Funcionario funcionario = Funcionario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .senha(dto.getSenha())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .sexo(dto.getSexo())
                .salario(dto.getSalario())
                .supervisor(dto.getSupervisor())
                .build();

        Funcionario funcionarioResponseDto = funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDto(funcionarioResponseDto);

    }

    public FuncionarioResponseDto atualizarFuncionario(Long id, FuncionarioRequestDto dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionarioExistente.setId(dto.getId());
        funcionarioExistente.setNome(dto.getNome());
        funcionarioExistente.setEmail(dto.getEmail());
        funcionarioExistente.setCpf(dto.getCpf());
        funcionarioExistente.setTelefone(dto.getTelefone());
        funcionarioExistente.setDataNascimento(dto.getDataNascimento());
        funcionarioExistente.setSexo(dto.getSexo());
        funcionarioExistente.setSalario(dto.getSalario());
        funcionarioExistente.setSupervisor(dto.getSupervisor());

//        Funcionario funcionarioEncontrado = Funcionario
//                    .builder()
//
//                    .nome(dto.getNome())
//                    .cpf(dto.getCpf())
//                    .telefone(dto.getTelefone())
//                    .dataNascimento(dto.getDataNascimento())
//                    .sexo(dto.getSexo())
//                    .salario(dto.getSalario())
//                    .build();

        FuncionarioResponseDto funcionarioAtualizado = new FuncionarioResponseDto(funcionarioRepository.save(funcionarioExistente));
        return funcionarioAtualizado;
    }

    public void apagarFuncionario(Long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o id:" + id);
        }
    }

    public void criptografarSenha(FuncionarioRequestDto dto) {
        // Criptografando a senha do usuário
        dto.setSenha(enconder.encode(dto.getSenha()));
    }
}


