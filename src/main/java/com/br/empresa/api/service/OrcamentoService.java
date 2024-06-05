package com.br.empresa.api.service;

import com.br.empresa.api.dto.OrcamentoRequestDto;
import com.br.empresa.api.dto.OrcamentoResponseDto;
import com.br.empresa.api.entity.Orcamento;
import com.br.empresa.api.repository.OrcamentoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrcamentoService {

    @Autowired
    OrcamentoRepository orcamentoRepository;

    private final ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(OrcamentoService.class);


    @Transactional
    public OrcamentoResponseDto cadastrarOrcamento(OrcamentoRequestDto dto) {

        Orcamento orcamento = Orcamento.builder()
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .dataInicio(dto.getDataInicio())
                .dataFinal(dto.getDataFinal())
                .build();
        Orcamento orcamentoSalvo = orcamentoRepository.save(orcamento);

        logger.info("Orcamento salvo com sucesso: {}", orcamentoSalvo);
        return mapper.map(orcamentoSalvo, OrcamentoResponseDto.class);
    }
}
