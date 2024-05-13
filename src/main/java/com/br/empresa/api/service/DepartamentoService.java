package com.br.empresa.api.service;


import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    DepartamentoRepository departamentoRepository;

    public List<DepartamentoResponseDto> buscarDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentos.stream()
                .map(departamento -> mapper.map(
                        departamento, DepartamentoResponseDto.class
                )).collect(Collectors.toList());
    }

    public DepartamentoResponseDto buscarDepartamentoPorId(Long id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        DepartamentoResponseDto departamentoResponseVo = new DepartamentoResponseDto(departamento.get());
        if (departamento.isPresent()) {
            return departamentoResponseVo;
        }
        throw new RuntimeException();
    }

    public DepartamentoResponseDto cadastrarDepartamento(DepartamentoRequestDto dto) {
        Departamento departamento = Departamento.builder()
                .nome(dto.getNome())
                .numero(dto.getNumero())
                .build();

        Departamento departamentoSalvo = departamentoRepository.save(departamento);
        return new DepartamentoResponseDto(departamentoSalvo);
    }

    public DepartamentoResponseDto atualizarDepartamento(DepartamentoRequestDto dto) {
        return departamentoRepository.findById(dto.getId())
                .map(departamento -> {
                    departamento.setNome(dto.getNome());
                    departamento.setNumero(dto.getNumero());
                    return new DepartamentoResponseDto(departamentoRepository.save(departamento));
                })
                .orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado com o id: " + dto.getId()));
    }

    public void apagarDepartamento(Long id) {
        Optional<Departamento> optionalFuncionario = departamentoRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            departamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o id:" + id);
        }
    }

}
