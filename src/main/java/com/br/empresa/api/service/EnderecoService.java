package com.br.empresa.api.service;

import com.br.empresa.api.dto.EnderecoRequestDto;
import com.br.empresa.api.dto.EnderecoResponseDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.entity.Endereco;
import com.br.empresa.api.entity.Funcionario;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.EnderecoRepository;
import com.br.empresa.api.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    private final ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(EnderecoService.class);


    public List<EnderecoResponseDto> buscarEnderecos() {
        try {
            List<Endereco> enderecos = enderecoRepository.findAll();
            if (enderecos.isEmpty()) {
                logger.info("Nenhum endereço encontrado");
                return Collections.emptyList();
            }
            return enderecos.stream()
                    .map(endereco -> mapper.map(endereco, EnderecoResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erro ao buscar endereços ", e);
            throw new RuntimeException("Erro ao buscar endereços", e);
        }
    }

    public EnderecoResponseDto buscarEnderecoPorId(Long id) {
        logger.info("Buscando endereco com ID: {}", id);
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            Endereco endereco = enderecoOptional.get();
            return mapper.map(endereco, EnderecoResponseDto.class);
        } else {
            logger.info("Endereco não encontrado para o ID: {}", id);
            throw new EntityNotFoundException("Endereco não encontrado para o ID: {}" + id);
        }
    }

    public EnderecoResponseDto cadastrarEndereco(EnderecoRequestDto dto) {

        Endereco endereco = Endereco.builder()
                .pais(dto.getPais())
                .uf(dto.getUf())
                .cidade(dto.getCidade())
                .rua(dto.getRua())
                .cep(dto.getCep())
                .build();

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        logger.info("Endereco salvo com sucesso: {}", enderecoSalvo);
        return mapper.map(enderecoSalvo, EnderecoResponseDto.class);
    }

    public EnderecoResponseDto atualizarEndereco(EnderecoRequestDto dto) {
        return enderecoRepository.findById(dto.getId())
                .map(endereco -> {
                    endereco.setPais(endereco.getPais());
                    endereco.setUf(endereco.getUf());
                    endereco.setCidade(endereco.getCidade());
                    endereco.setRua(endereco.getRua());
                    endereco.setCep(endereco.getCep());
                    Endereco enderecoAtualizado = enderecoRepository.save(endereco);
                    return mapper.map(enderecoAtualizado, EnderecoResponseDto.class);
                })
                .orElseThrow(() -> {
                    logger.error("Endereco não encontrado com o id: {}", dto.getId());
                    return new EntityNotFoundException("Endereco não encontrado com o id: " + dto.getId());
                });
    }
}

