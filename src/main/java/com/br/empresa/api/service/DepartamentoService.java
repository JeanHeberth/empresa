package com.br.empresa.api.service;


import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
public class DepartamentoService {

    private final ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(DepartamentoService.class);


    @Autowired
    DepartamentoRepository departamentoRepository;

    public List<DepartamentoResponseDto> buscarDepartamentos() {
        try {
            List<Departamento> departamentos = departamentoRepository.findAll();
            if (departamentos.isEmpty()) {
                logger.info("Nenhum departamento encontrado.");
                return Collections.emptyList();
            }
            return departamentos.stream()
                    .map(departamento -> mapper.map(departamento, DepartamentoResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erro ao buscar departamentos: ", e);
            throw new RuntimeException("Erro ao buscar departamentos", e);
        }
    }

    public DepartamentoResponseDto buscarDepartamentoPorId(Long id) {
        logger.info("Buscando departamento com ID: {}", id);
        Optional<Departamento> departamentoOpt = departamentoRepository.findById(id);

        if (departamentoOpt.isPresent()) {
            Departamento departamento = departamentoOpt.get();
            logger.info("Departamento encontrado: {}", departamento);
            return mapper.map(departamento, DepartamentoResponseDto.class);
        } else {
            logger.error("Departamento não encontrado para o ID: {}", id);
            throw new EntityNotFoundException("Departamento não encontrado para o ID: " + id);
        }
    }

    @Transactional
    public DepartamentoResponseDto cadastrarDepartamento(DepartamentoRequestDto dto) {
        logger.info("Cadastrando novo departamento: {}", dto);

        // Validação dos dados do DTO
        if (dto.getNome() == null || dto.getNome().isEmpty()) {
            logger.error("O nome do departamento é obrigatório.");
            throw new IllegalArgumentException("O nome do departamento é obrigatório.");
        }

        if (dto.getNumero() == null) {
            logger.error("O número do departamento é obrigatório.");
            throw new IllegalArgumentException("O número do departamento é obrigatório.");
        }

        // Construção da entidade Departamento
        Departamento departamento = Departamento.builder()
                .nome(dto.getNome())
                .numero(String.valueOf(dto.getNumero()))
                .build();

        // Salvamento do departamento
        Departamento departamentoSalvo = departamentoRepository.save(departamento);
        logger.info("Departamento salvo com sucesso: {}", departamentoSalvo);

        // Mapeamento da entidade salva para o DTO de resposta
        return mapper.map(departamentoSalvo, DepartamentoResponseDto.class);
    }

    @Transactional
    public DepartamentoResponseDto atualizarDepartamento(DepartamentoRequestDto dto) {
        logger.info("Atualizando departamento com ID: {}", dto.getId());

        return departamentoRepository.findById(dto.getId())
                .map(departamento -> {
                    departamento.setNome(dto.getNome());
                    departamento.setNumero(String.valueOf(dto.getNumero()));
                    Departamento departamentoAtualizado = departamentoRepository.save(departamento);
                    logger.info("Departamento atualizado com sucesso: {}", departamentoAtualizado);
                    return mapper.map(departamentoAtualizado, DepartamentoResponseDto.class);
                })
                .orElseThrow(() -> {
                    logger.error("Departamento não encontrado com o id: {}", dto.getId());
                    return new EntityNotFoundException("Departamento não encontrado com o id: " + dto.getId());
                });
    }

    @Transactional
    public void apagarDepartamento(Long id) {
        logger.info("Tentando apagar departamento com ID: {}", id);

        Optional<Departamento> optionalDepartamento = departamentoRepository.findById(id);
        if (optionalDepartamento.isPresent()) {
            departamentoRepository.deleteById(id);
            logger.info("Departamento com ID {} apagado com sucesso.", id);
        } else {
            logger.error("Departamento não encontrado com o id: {}", id);
            throw new EntityNotFoundException("Departamento não encontrado com o id: " + id);
        }
    }
}
