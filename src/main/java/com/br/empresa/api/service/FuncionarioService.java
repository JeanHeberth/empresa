package com.br.empresa.api.service;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.dto.PessoaResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.entity.Endereco;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.entity.Pessoa;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.DepartamentoRepository;
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
    @Autowired
    private DepartamentoRepository departamentoRepository;


    public List<FuncionarioResponseDto> buscarFuncionarios() {
        logger.info("Buscando todos os funcionários: ");
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioResponseDto> responseDtos = funcionarios.stream()
                .map(funcionario -> {
                    FuncionarioResponseDto dto = mapper.map(funcionario, FuncionarioResponseDto.class);
                    if (funcionario.getSupervisor() != null) {
                        dto.setNomeSupervisor(funcionario.getSupervisor().getPessoa().getNome());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return responseDtos;

    }

    public FuncionarioResponseDto buscarFuncionarioPorId(Long id) {

        logger.info("Buscando funcionário por id: " + id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com o id " + id + " não encontrado"));
        FuncionarioResponseDto responseDto = mapper.map(funcionario, FuncionarioResponseDto.class);

        if (funcionario.getSupervisor() != null) {
            responseDto.setNomeSupervisor(funcionario.getSupervisor().getPessoa().getNome());
        }
        return responseDto;
    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto dto) {

        // Verificação de email duplicado
        Optional<Funcionario> funcionarioOptionalEmail = funcionarioRepository.findByEmailCorporativo(dto.getEmailCorporativo());
        if (funcionarioOptionalEmail.isPresent()) {
            throw new EntityNotFoundException("Já existe um usuário com o email cadastrado");
        }

        logger.info("Cadastrando funcionário: " + dto);
        Funcionario funcionario = Funcionario.builder()
                .matricula(matriculaService.gerarMatricula(dto.getDataAdmissao(), dto.getCpf()))
                .cargo(dto.getCargo())
                .emailCorporativo(dto.getEmailCorporativo())
                .dataAdmissao(dto.getDataAdmissao())
                .salario(dto.getSalario())
                .build();

        // Verificação se a pessoa tem id
        Pessoa pessoa = pessoaRepository.findByCpf(dto.getCpf())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa com o cpf " + dto.getCpf() + " não encontrada"));
        funcionario.setPessoa(pessoa);

        // Departamento
        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento com o id " + dto.getIdDepartamento() + " não encontrado"));
        funcionario.setDepartamento(departamento);

        // Verificação e associação do supervisor, se existir
        if (dto.getMatriculaSupervisor() != null) {
            Funcionario supervisor = funcionarioRepository.findByMatricula(dto.getMatriculaSupervisor())
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário com o id " + dto.getMatriculaSupervisor() + " não encontrado"));
            funcionario.setSupervisor(supervisor);
            supervisor.adicionarSubordinados(funcionario);
        }

        // Cadastramento do funcionário
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        // Mapeamento para DTO de resposta
        FuncionarioResponseDto responseDto = mapper.map(funcionarioSalvo, FuncionarioResponseDto.class);

        // Preencha manualmente o nome do supervisor, se houver
        if (funcionarioSalvo.getSupervisor() != null) {
            responseDto.setNomeSupervisor(funcionarioSalvo.getSupervisor().getPessoa().getNome());
        }

        return responseDto;
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
//        if (dto.getIdSupervisor() != null) {
//            funcionarioExistente(false);
//        } else {
//            throw new EntityNotFoundException("Supervisor com o id " + dto.getIdSupervisor() + " não encontrado");
//        }

        //Verificação se a pessoa tem id
        Optional<Pessoa> pessoa = pessoaRepository.findById(dto.getIdPessoa());
        if (pessoa.isPresent()) {
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
        Optional<Pessoa> pessoas = pessoaRepository.findByCpf(cpf);
        if (pessoas.isEmpty()) {
            throw new EntityNotFoundException("Pessoa com o cpf " + cpf + " não encontrada");
        }
        logger.info("Pessoas encontradas");
        return pessoas.stream()
                .map(pessoa -> mapper.map(pessoa, FuncionarioResponseDto.class))
                .collect(Collectors.toList());
    }

}


