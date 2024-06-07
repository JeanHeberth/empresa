package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoRequestDto {

    private Long id;
    private String nome;
    private Double valorProjeto;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private Long idDepartamento;
}
