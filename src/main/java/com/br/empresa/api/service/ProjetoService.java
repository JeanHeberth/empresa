package com.br.empresa.api.service;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.dto.ProjetoRequestDto;
import com.br.empresa.api.dto.ProjetoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.entity.Orcamento;
import com.br.empresa.api.entity.Projeto;
import com.br.empresa.api.exception.EntityNotFoundException;
import com.br.empresa.api.repository.DepartamentoRepository;
import com.br.empresa.api.repository.ProjetoRepository;
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
public class ProjetoService {

    @Autowired
    ProjetoRepository projetoRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;

    private final ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(ProjetoService.class);


    public List<ProjetoResponseDto> buscarProjetos() {
        try {
            List<Projeto> projetos = projetoRepository.findAll();
            if (projetos.isEmpty()) {
                logger.info("Nenhum projeto encontrado");
                return Collections.emptyList();
            }
            return projetos.stream()
                    .map(projeto -> mapper.map(projeto, ProjetoResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Erro ao buscar projetos ", e);
            throw new RuntimeException("Erro ao buscar projetos", e);
        }
    }

    public ProjetoResponseDto buscarProjetoPorId(Long id) {
        logger.info("Buscando projeto com ID: {}", id);
        Optional<Projeto> projetoOptional = projetoRepository.findById(id);
        if (projetoOptional.isPresent()) {
            Projeto projeto = projetoOptional.get();
            return mapper.map(projeto, ProjetoResponseDto.class);
        } else {
            logger.info("Orçamento não encontrado para o ID: {}", id);
            throw new EntityNotFoundException("Orçamento não encontrado para o ID: {}" + id);
        }
    }

    public ProjetoResponseDto cadastrarProjeto(ProjetoRequestDto dto) {

        Projeto projeto = Projeto.builder()
                .nome(dto.getNome())
                .valorProjeto(dto.getValorProjeto())
                .dataInicio(dto.getDataInicio())
                .dataFinal(dto.getDataFinal())
                .build();
        Optional<Departamento> departamento = departamentoRepository.findById(dto.getIdDepartamento());
        projeto.setDepartamento(departamento.get());
        Projeto projetoSalvo = projetoRepository.save(projeto);

        logger.info("Projeto salvo com sucesso: {}", projetoSalvo);
        return mapper.map(projetoSalvo, ProjetoResponseDto.class);
    }

    public ProjetoResponseDto atualizarProjeto(ProjetoRequestDto dto) {
        return projetoRepository.findById(dto.getId())
                .map(projeto -> {
                    projeto.setNome(dto.getNome());
                    projeto.setValorProjeto(dto.getValorProjeto());
                    projeto.setDataInicio(dto.getDataInicio());
                    projeto.setDataFinal(dto.getDataFinal());

                    // Buscar e associar o Departamento
                    Optional<Departamento> departamentoOptional = departamentoRepository.findById(dto.getIdDepartamento());
                    projeto.setDepartamento(departamentoOptional.get());

                    Projeto projetoAtualizado = projetoRepository.save(projeto);
                    return mapper.map(projetoAtualizado, ProjetoResponseDto.class);
                })
                .orElseThrow(() -> {
                    logger.error("Projeto não encontrado com o id: {}", dto.getId());
                    return new EntityNotFoundException("Projeto não encontrado com o id: " + dto.getId());
                });
    }

    public void deletarProjeto(Long id) {
        Optional<Projeto> optionalProjeto = this.projetoRepository.findById(id);
        if (optionalProjeto.isPresent()) {
            this.projetoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Projeto não encontrado");
        }
    }
}

