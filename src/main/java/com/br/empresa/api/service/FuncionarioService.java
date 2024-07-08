package com.br.empresa.api.service;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.entity.Endereco;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.EnderecoRepository;
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

    @Autowired
    private EnderecoRepository enderecoRepository;

//    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

    private ModelMapper mapper = new ModelMapper();


//    public Funcionario autenticar(String email, String senha) {
//        Optional<Funcionario> usuario = funcionarioRepository.findByEmail(email);
//        if (!usuario.isPresent()) {
//            throw new EntityNotFoundException("Usuário não encontrado para o email informado.");
//        }
//        if (!usuario.get().getSenha().equals(senha)) {
//            throw new EntityNotFoundException("Senha inválida.");
//        }
//        return usuario.get();
//    }


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

        // Verificação de email duplicado
        Optional<Funcionario> funcionarioOptionalEmail = funcionarioRepository.findByEmail(dto.getEmail());
        if (funcionarioOptionalEmail.isPresent()) {
            throw new EntityNotFoundException("Já existe um usuário com o email ou endereço cadastrado");
        }

        // Verificação de CPF duplicado
        Optional<Funcionario> funcionarioOptionalCpf = funcionarioRepository.findByCpf(dto.getCpf());
        if (funcionarioOptionalCpf.isPresent()) {
            throw new EntityNotFoundException("Já existe um usuário com o CPF cadastrado");
        }


        Funcionario funcionario = Funcionario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .cargo(dto.getCargo())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .sexo(dto.getSexo())
                .salario(dto.getSalario())
                .build();

        // Verificação e associação do supervisor, se existir
        if (dto.getIdSupervisor() != null) {
            Optional<Funcionario> optionalSupervisor = funcionarioRepository.findById(dto.getIdSupervisor());
            if (optionalSupervisor.isPresent()) {
                Funcionario supervisor = optionalSupervisor.get();
                supervisor.adicionarSubordinados(funcionario);
            } else {
                throw new EntityNotFoundException("Supervisor com o id " + dto.getIdSupervisor() + " não encontrado");
            }
        }

        // Verificação se o endereço já está associado a outro funcionário
        Optional<Funcionario> funcionarioOptionalEndereco = funcionarioRepository.findByEnderecoId(dto.getIdEndereco());
        if (funcionarioOptionalEndereco.isPresent()) {
            throw new EntityNotFoundException("Já existe um usuário associado ao endereço fornecido");
        }

        // Verificação e associação do endereço
        Optional<Endereco> endereco = enderecoRepository.findById(dto.getIdEndereco());
        if (endereco.isPresent()) {
            endereco.get().setFuncionario(funcionario);
            funcionario.setEndereco(endereco.get());
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
            return new FuncionarioResponseDto(funcionarioSalvo);
        } else {
            throw new RuntimeException("Endereço com o id" + dto.getIdEndereco() + " não encontrado");
        }
    }

    public FuncionarioResponseDto atualizarFuncionario(Long id, FuncionarioRequestDto dto) {
        // Verificação se o funcionário existe
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if (!funcionarioOptional.isPresent()) {
            throw new EntityNotFoundException("Funcionário com o id " + id + " não encontrado");
        }

        Funcionario funcionarioExistente = funcionarioOptional.get();

        // Verificação de email duplicado
        Optional<Funcionario> funcionarioOptionalEmail = funcionarioRepository.findByEmail(dto.getEmail());
        if (funcionarioOptionalEmail.isPresent() && !funcionarioOptionalEmail.get().getId().equals(id)) {
            throw new EntityNotFoundException("Já existe um usuário com o email cadastrado");
        }

        // Verificação de CPF duplicado
        Optional<Funcionario> funcionarioOptionalCpf = funcionarioRepository.findByCpf(dto.getCpf());
        if (funcionarioOptionalCpf.isPresent() && !funcionarioOptionalCpf.get().getId().equals(id)) {
            throw new EntityNotFoundException("Já existe um usuário com o CPF cadastrado");
        }

        funcionarioExistente.setId(dto.getId());
        funcionarioExistente.setNome(dto.getNome());
        funcionarioExistente.setEmail(dto.getEmail());
        funcionarioExistente.setCpf(dto.getCpf());
        funcionarioExistente.setCargo(dto.getCargo());
        funcionarioExistente.setTelefone(dto.getTelefone());
        funcionarioExistente.setDataNascimento(dto.getDataNascimento());
        funcionarioExistente.setSexo(dto.getSexo());
        funcionarioExistente.setSalario(dto.getSalario());

        // Verificação e associação do supervisor, se existir
        if (dto.getIdSupervisor() != null) {
            Optional<Funcionario> optionalSupervisor = funcionarioRepository.findById(dto.getIdSupervisor());
            if (optionalSupervisor.isPresent()) {
                Funcionario supervisor = optionalSupervisor.get();
                supervisor.adicionarSubordinados(funcionarioExistente);
                funcionarioExistente.setSupervisor(supervisor); // Adiciona supervisor ao funcionário
            } else {
                throw new EntityNotFoundException("Supervisor com o id " + dto.getIdSupervisor() + " não encontrado");
            }
        } else {
            funcionarioExistente.setSupervisor(null); // Remove o supervisor se não for fornecido
        }

        // Verificação se o endereço já está associado a outro funcionário
        Optional<Funcionario> funcionarioOptionalEndereco = funcionarioRepository.findByEnderecoId(dto.getIdEndereco());
        if (funcionarioOptionalEndereco.isPresent() && !funcionarioOptionalEndereco.get().getId().equals(id)) {
            throw new EntityNotFoundException("Já existe um usuário associado ao endereço fornecido");
        }

        // Verificação e associação do endereço
        Optional<Endereco> endereco = enderecoRepository.findById(dto.getIdEndereco());
        if (endereco.isPresent()) {
            endereco.get().setFuncionario(funcionarioExistente);
            funcionarioExistente.setEndereco(endereco.get());
        } else {
            throw new RuntimeException("Endereço com o id " + dto.getIdEndereco() + " não encontrado");
        }

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioExistente);
        return new FuncionarioResponseDto(funcionarioSalvo);
    }

    public void apagarFuncionario(Long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o id:" + id);
        }
    }


}


