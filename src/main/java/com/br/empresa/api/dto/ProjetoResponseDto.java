package com.br.empresa.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResponseDto {

    private Long id;
    private String nome;
    private Double valorProjeto;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;
    private Long idDepartamento;
}
