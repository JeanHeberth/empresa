package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequestDto {

    private Long id;

    private String nome;

    private Long numero;

    public DepartamentoRequestDto(String nome, String numero) {
    }

    public DepartamentoRequestDto(Long id, String nome, String numero) {
    }
}
