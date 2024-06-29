package com.br.empresa.api.dto;

import com.br.empresa.api.entity.Departamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponseDto {

    private Long id;

    private String nome;

    private String numero;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    public DepartamentoResponseDto(Departamento departamento) {
        this.id = departamento.getId();
        this.nome = departamento.getNome();
        this.numero = departamento.getNumero();
        this.dataCadastro = departamento.getDataCadastro();
    }


}
