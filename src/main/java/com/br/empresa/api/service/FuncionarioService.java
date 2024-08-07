package com.br.empresa.api.service;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.dto.PessoaResponseDto;
import com.br.empresa.api.entity.Endereco;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.entity.Pessoa;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.EnderecoRepository;
import com.br.empresa.api.repository.FuncionarioRepository;
import com.br.empresa.api.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrcamentoService.class);

    private ModelMapper mapper = new ModelMapper();
    @Autowired
    private PessoaService pessoaService;


    public List<FuncionarioResponseDto> buscarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioResponseDto.class))
                .collect(Collectors.toList());

    }

    public FuncionarioResponseDto buscarFuncionarioPorId(Long id) {

        logger.info("Buscando funcionário por id: " + id);
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if (!funcionario.isPresent()) {
            throw new EntityNotFoundException("Funcionário com o id " + id + " não encontrado");
        }
        return mapper.map(funcionario.get(), FuncionarioResponseDto.class);

    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto dto) {

        // Verificação de email duplicado
        Optional<Funcionario> funcionarioOptionalEmail = funcionarioRepository.findByEmailCorporativo(dto.getEmailCorporativo());
        if (funcionarioOptionalEmail.isPresent()) {
            throw new EntityNotFoundException("Já existe um usuário com o emailcadastrado");
        }

        logger.info("Cadastrando funcionário: " + dto);
        Funcionario funcionario = Funcionario.builder()
                .matricula(matriculaService.gerarMatricula(dto.getDataAdmissao(), dto.getCpf()))
                .cargo(dto.getCargo())
                .emailCorporativo(dto.getEmailCorporativo())
                .dataAdmissao(dto.getDataAdmissao())
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
        //Verificação se a pessoa tem id
        Optional<Pessoa> pessoa = pessoaRepository.findById(dto.getIdPessoa());
        if (pessoa.isPresent()) {
            pessoa.get().setFuncionario(funcionario);
            funcionario.setPessoa(pessoa.get());
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
            return mapper.map(funcionarioSalvo, FuncionarioResponseDto.class);
        } else {
            throw new EntityNotFoundException("Pessoa com o id " + dto.getIdPessoa() + " não encontrada");
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
        Optional<Funcionario> funcionarioOptionalEmail = funcionarioRepository.findByEmailCorporativo(dto.getEmailCorporativo());
        if (funcionarioOptionalEmail.isPresent() && !funcionarioOptionalEmail.get().getId().equals(id)) {
            throw new EntityNotFoundException("Já existe um usuário com o email corporativo cadastrado");
        }

        // Atualização do funcionário
        funcionarioExistente.setId(dto.getId());
        funcionarioExistente.setEmailCorporativo(dto.getEmailCorporativo());
        funcionarioExistente.setCargo(dto.getCargo());
        funcionarioExistente.setDataAdmissao(dto.getDataAdmissao());
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

        //Verificação se a pessoa tem id
        Optional<Pessoa> pessoa = pessoaRepository.findById(dto.getIdPessoa());
        if (pessoa.isPresent()) {
            pessoa.get().setFuncionario(funcionarioExistente);
            funcionarioExistente.setPessoa(pessoa.get());
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioExistente);
            logger.info("Funcionário salvo com sucesso: {}", funcionarioSalvo);
            return mapper.map(funcionarioSalvo, FuncionarioResponseDto.class);
        } else {
            throw new EntityNotFoundException("Pessoa com o id " + dto.getIdPessoa() + " não encontrada");
        }


    }

    public void apagarFuncionario(Long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o id:" + id);
        }
    }

    public List<FuncionarioResponseDto> buscarPessoasPorCpf(String cpf) {
        List<Pessoa> pessoas = pessoaRepository.findByCpf(cpf);
        if (pessoas.isEmpty()) {
            throw new EntityNotFoundException("Pessoa com o cpf " + cpf + " não encontrada");
        }
        logger.info("Pessoas encontradas");
        return pessoas.stream()
                .map(pessoa -> mapper.map(pessoa, FuncionarioResponseDto.class))
                .collect(Collectors.toList());
    }

//    private String getMatricula(){
//        return  UUID.randomUUID().toString().substring(0, 5).toUpperCase();
//    }
}


