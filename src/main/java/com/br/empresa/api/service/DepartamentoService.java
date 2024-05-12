package com.br.empresa.api.service;


import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    private ModelMapper mapper = new ModelMapper();

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
        Departamento departamentoExistente = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        return new DepartamentoResponseDto(departamentoExistente);
    }


    public DepartamentoResponseDto cadastrarDepartamento(DepartamentoRequestDto dto) {
        Departamento departamento = Departamento.builder()
                .nome(dto.getNome())
                .numero(dto.getNumero())
                .build();

        Departamento departamentoSalvo = departamentoRepository.save(departamento);
        return new DepartamentoResponseDto(departamentoSalvo);
    }

    public void apagarFuncionario(Long id) {
        Optional<Departamento> optionalFuncionario = departamentoRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            departamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado com o id:" + id);
        }
    }


    public DepartamentoResponseDto atualizarDepartamento(Long id, DepartamentoRequestDto dto) {
        Departamento departamentoExistente = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        departamentoExistente.setId(departamentoExistente.getId());
        departamentoExistente.setNome(dto.getNome());
        departamentoExistente.setNumero(dto.getNumero());

        DepartamentoResponseDto departamentoAtualizado = new DepartamentoResponseDto(departamentoRepository.save(departamentoExistente));
        return departamentoAtualizado;
    }
}
