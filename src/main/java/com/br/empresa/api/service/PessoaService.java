package com.br.empresa.api.service;

import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.dto.PessoaRequestDto;
import com.br.empresa.api.dto.PessoaResponseDto;
import com.br.empresa.api.entity.Orcamento;
import com.br.empresa.api.entity.Pessoa;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;
    private final ModelMapper mapper = new ModelMapper();

    private static final Logger logger = LoggerFactory.getLogger(OrcamentoService.class);

    public List<PessoaResponseDto> buscarPessoas() {
        try {
            List<Pessoa> pessoas = pessoaRepository.findAll();
            if (pessoas.isEmpty()) {
                logger.info("Nenhuma pessoa encontrada");
                return Collections.emptyList();
            }

            logger.info("Pessoas encontradas");
            return pessoas.stream()
                    .map(pessoa -> mapper.map(pessoa, PessoaResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erro ao buscar pessoas ", e);
            throw new RuntimeException("Erro ao buscar pessoas", e);
        }
    }

    public PessoaResponseDto buscarPessoaPorId(Long id) {
        logger.info("Buscando orçamento com ID: {}", id);
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            return mapper.map(pessoa, PessoaResponseDto.class);
        } else {
            logger.info("Pessoas não encontrado para o ID: {}", id);
            throw new EntityNotFoundException("Pessoas não encontrado para o ID: {}" + id);
        }
    }

    public void apagarPessoa(Long id) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        if (optionalPessoa.isPresent()) {
            pessoaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa não encontrada com o id:" + id);
        }
    }

    public PessoaResponseDto cadastrarPessoa(PessoaRequestDto dto) {
        logger.info("Cadastrando pessoa");
        Pessoa pessoa = Pessoa.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDataNascimento())
                .sexo(dto.getSexo())
                .build();
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        logger.info("Pessoa salvo com sucesso: {}", pessoaSalva);
        return mapper.map(pessoaSalva, PessoaResponseDto.class);

    }

    public PessoaResponseDto atualizarPessoa(Long id, PessoaRequestDto dto) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(dto.getNome());
                    pessoa.setCpf(dto.getCpf());
                    pessoa.setEmail(dto.getEmail());
                    pessoa.setDataNascimento(dto.getDataNascimento());
                    pessoa.setSexo(dto.getSexo());
                    pessoaRepository.save(pessoa);
                    return new PessoaResponseDto(pessoa);
                }).orElseThrow();
    }
}
