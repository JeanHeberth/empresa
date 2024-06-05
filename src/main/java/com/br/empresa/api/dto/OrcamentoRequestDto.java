package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrcamentoRequestDto {

    private Long id;
    private String descricao;
    private Double valor;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
}
