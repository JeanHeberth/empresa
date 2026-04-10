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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    private static final Logger logger = LoggerFactory.getLogger(DepartamentoService.class);

    private final DepartamentoRepository departamentoRepository;
    private final ModelMapper mapper;

    public DepartamentoService(DepartamentoRepository departamentoRepository, ModelMapper mapper) {
        this.departamentoRepository = departamentoRepository;
        this.mapper = mapper;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Page<DepartamentoResponseDto> buscarDepartamentos(Pageable pageable) {
        logger.info("Buscando departamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return departamentoRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public DepartamentoResponseDto buscarDepartamentoPorId(Long id) {
        logger.info("Buscando departamento com ID: {}", id);

        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Departamento não encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Departamento não encontrado com o ID: " + id);
                });

        return toResponseDto(departamento);
    }

    @Transactional
    public DepartamentoResponseDto cadastrarDepartamento(DepartamentoRequestDto dto) {
        logger.info("Cadastrando novo departamento: nome={}, numero={}", dto.getNome(), dto.getNumero());

        validarDto(dto);

        Departamento departamento = Departamento.builder()
                .nome(dto.getNome().trim())
                .numero(String.valueOf(dto.getNumero()))
                .build();

        Departamento salvo = departamentoRepository.save(departamento);

        logger.info("Departamento salvo com sucesso. ID: {}", salvo.getId());
        return toResponseDto(salvo);
    }

    @Transactional
    public DepartamentoResponseDto atualizarDepartamento(Long id, DepartamentoRequestDto dto) {
        logger.info("Atualizando departamento com ID: {}", id);

        validarDto(dto);

        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Departamento não encontrado com o ID: {}", id);
                    return new EntityNotFoundException("Departamento não encontrado com o id: " + id);
                });

        departamento.setNome(dto.getNome().trim());
        departamento.setNumero(String.valueOf(dto.getNumero()));

        Departamento atualizado = departamentoRepository.save(departamento);

        logger.info("Departamento atualizado com sucesso. ID: {}", atualizado.getId());
        return toResponseDto(atualizado);
    }

    @Transactional
    public void apagarDepartamento(Long id) {
        logger.info("Tentando apagar departamento com ID: {}", id);

        if (!departamentoRepository.existsById(id)) {
            logger.error("Departamento não encontrado com o ID: {}", id);
            throw new EntityNotFoundException("Departamento não encontrado com o id: " + id);
        }

        departamentoRepository.deleteById(id);
        logger.info("Departamento com ID {} apagado com sucesso.", id);
    }

    private DepartamentoResponseDto toResponseDto(Departamento departamento) {
        return mapper.map(departamento, DepartamentoResponseDto.class);
    }

    private void validarDto(DepartamentoRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("O corpo da requisição é obrigatório.");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do departamento é obrigatório.");
        }

        if (dto.getNumero() == null) {
            throw new IllegalArgumentException("O número do departamento é obrigatório.");
        }
    }
}