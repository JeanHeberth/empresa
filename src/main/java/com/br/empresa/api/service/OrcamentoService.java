package com.br.empresa.api.service;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.entity.Orcamento;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.DepartamentoRepository;
import com.br.empresa.api.repository.OrcamentoRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrcamentoService {

    @Autowired
    OrcamentoRepository orcamentoRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;

    private final ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(OrcamentoService.class);


    public List<OrcamentoResponseDto> buscarOrcamentos() {
        try {
            List<Orcamento> orcamentos = orcamentoRepository.findAll();
            if (orcamentos.isEmpty()) {
                logger.info("Nenhum orçamento encontrado");
                return Collections.emptyList();
            }
            return orcamentos.stream()
                    .map(orcamento -> mapper.map(orcamento, OrcamentoResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erro ao buscar orçamentos ", e);
            throw new RuntimeException("Erro ao buscar orçamentos", e);
        }
    }

    public OrcamentoResponseDto buscarOrcamentoPorId(Long id) {
        logger.info("Buscando orçamento com ID: {}", id);
        Optional<Orcamento> orcamentoOptional = orcamentoRepository.findById(id);
        if (orcamentoOptional.isPresent()) {
            Orcamento orcamento = orcamentoOptional.get();
            return mapper.map(orcamento, OrcamentoResponseDto.class);
        } else {
            logger.info("Orçamento não encontrado para o ID: {}", id);
            throw new EntityNotFoundException("Orçamento não encontrado para o ID: {}" + id);
        }
    }

    public OrcamentoResponseDto cadastrarOrcamento(OrcamentoRequestDto dto) {

        logger.info("Cadastrando orçamento: {}", dto);
        Orcamento orcamento = Orcamento.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .dataInicio(dto.getDataInicio())
                .dataFinal(dto.getDataFinal())
                .build();
        Optional<Departamento> departamento = departamentoRepository.findById(dto.getIdDepartamento());
        orcamento.setDepartamento(departamento.get());
        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);

        logger.info("Orcamento salvo com sucesso: {}", orcamentoSalvo);
        return mapper.map(orcamentoSalvo, OrcamentoResponseDto.class);
    }

    public OrcamentoResponseDto atualizarOrcamento(OrcamentoRequestDto dto) {
        return orcamentoRepository.findById(dto.getId())
                .map(orcamento -> {
                    orcamento.setDescricao(dto.getDescricao());
                    orcamento.setValor(dto.getValor());
                    orcamento.setDataInicio(dto.getDataInicio());
                    orcamento.setDataFinal(dto.getDataFinal());
                    Orcamento orcamentoAtualizado = orcamentoRepository.save(orcamento);
                    return mapper.map(orcamentoAtualizado, OrcamentoResponseDto.class);
                })
                .orElseThrow(() -> {
                    logger.error("Orcamento não encontrado com o id: {}", dto.getId());
                    return new EntityNotFoundException("Orçamento não encontrado com o id: " + dto.getId());
                });
    }

    public void deletarOrcamento(Long id) {
        logger.info("Tentando apagar orcamento com ID: {}", id);
        orcamentoRepository.deleteById(id);
        logger.info("Orcamento com ID {} apagado com sucesso.", id);
    }
}

